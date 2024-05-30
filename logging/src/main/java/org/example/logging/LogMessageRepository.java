package org.example.logging;



import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogMessageRepository extends ElasticsearchRepository<LogMessage, String> {
}
