package com.Niraj17.NirajAI.service;

import com.Niraj17.NirajAI.dto.rag.CreateDocumentRequest;
import com.Niraj17.NirajAI.dto.rag.DocumentResponse;
import com.Niraj17.NirajAI.dto.rag.SearchResultResponse;
import com.Niraj17.NirajAI.entity.Document;
import com.Niraj17.NirajAI.entity.DocumentChunk;
import com.Niraj17.NirajAI.entity.Topic;
import com.Niraj17.NirajAI.repository.DocumentChunkRepository;
import com.Niraj17.NirajAI.repository.DocumentRepository;
import com.Niraj17.NirajAI.repository.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RAGService {

    private static final int CHUNK_SIZE = 3500;

    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository chunkRepository;
    private final TopicRepository topicRepository;

    public RAGService(DocumentRepository documentRepository, DocumentChunkRepository chunkRepository, TopicRepository topicRepository) {
        this.documentRepository = documentRepository;
        this.chunkRepository = chunkRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    public DocumentResponse ingest(CreateDocumentRequest request) {
        Topic topic = request.topicId() == null ? null : topicRepository.findById(request.topicId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        Document document = documentRepository.save(new Document(request.title().trim(), request.source().trim(), topic, request.content().trim()));
        List<DocumentChunk> chunks = chunk(document, request.content().trim());
        chunkRepository.saveAll(chunks);
        return new DocumentResponse(document.getId(), document.getTitle(), document.getSource(), topic == null ? null : topic.getId(), chunks.size(), document.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<DocumentResponse> listDocuments() {
        return documentRepository.findAll().stream()
                .map(document -> new DocumentResponse(document.getId(), document.getTitle(), document.getSource(),
                        document.getTopic() == null ? null : document.getTopic().getId(),
                        (int) chunkRepository.countByDocumentId(document.getId()), document.getCreatedAt()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SearchResultResponse> search(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        return chunkRepository.findTop8ByContentContainingIgnoreCaseOrderByChunkIndexAsc(query.trim()).stream()
                .map(chunk -> new SearchResultResponse(chunk.getDocument().getId(), chunk.getDocument().getTitle(),
                        chunk.getDocument().getSource(), chunk.getChunkIndex(), chunk.getContent()))
                .toList();
    }

    public String referenceContext(String query) {
        return search(query).stream()
                .map(result -> "[Reference: " + result.title() + " | " + result.source() + "]\n" + result.content())
                .reduce((left, right) -> left + "\n\n" + right)
                .orElse("");
    }

    private List<DocumentChunk> chunk(Document document, String content) {
        java.util.ArrayList<DocumentChunk> chunks = new java.util.ArrayList<>();
        for (int start = 0, index = 0; start < content.length(); start += CHUNK_SIZE, index++) {
            int end = Math.min(start + CHUNK_SIZE, content.length());
            chunks.add(new DocumentChunk(document, content.substring(start, end), index));
        }
        return chunks;
    }
}
