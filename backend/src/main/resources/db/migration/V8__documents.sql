CREATE TABLE documents (
    id UUID PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    source VARCHAR(500) NOT NULL,
    topic_id UUID REFERENCES topics (id) ON DELETE SET NULL,
    content VARCHAR(50000) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE document_chunks (
    id UUID PRIMARY KEY,
    document_id UUID NOT NULL REFERENCES documents (id) ON DELETE CASCADE,
    content VARCHAR(4000) NOT NULL,
    chunk_index INTEGER NOT NULL,
    metadata VARCHAR(2000)
);

CREATE INDEX idx_documents_topic_id ON documents (topic_id);
CREATE INDEX idx_document_chunks_document_id ON document_chunks (document_id);
