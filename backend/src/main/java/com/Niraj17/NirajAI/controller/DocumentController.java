package com.Niraj17.NirajAI.controller;

import com.Niraj17.NirajAI.dto.rag.CreateDocumentRequest;
import com.Niraj17.NirajAI.dto.rag.DocumentResponse;
import com.Niraj17.NirajAI.dto.rag.SearchResultResponse;
import com.Niraj17.NirajAI.service.RAGService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final RAGService ragService;

    public DocumentController(RAGService ragService) {
        this.ragService = ragService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentResponse ingest(@Valid @RequestBody CreateDocumentRequest request) {
        return ragService.ingest(request);
    }

    @GetMapping
    public List<DocumentResponse> list() {
        return ragService.listDocuments();
    }

    @GetMapping("/search")
    public List<SearchResultResponse> search(@RequestParam String q) {
        return ragService.search(q);
    }
}
