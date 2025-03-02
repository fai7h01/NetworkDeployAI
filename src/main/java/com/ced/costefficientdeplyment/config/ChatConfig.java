package com.ced.costefficientdeplyment.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, VectorStore vectorStore, ChatMemory chatMemory) {
        return builder
                .defaultSystem("""
                        "I need a structured feasibility analysis for a networking pipelineDTO deployment project in [address].
                         
                         There is an existing pipelineDTO that is [existing_pipeline_length] in length and an unused pipelineDTO (canalization) [distance_between_existing_and_empty_pipeline] away, with a length of [empty_pipeline_length]. Main goal of analysis is that it must compare what will be cost difference when deploying network from scratch and when deployment it though empty pipelineDTOS (canazilations).
                         
                         The analysis should be specific to only networking pipelineDTOS and should be detailed, location-specific, and include full source links where applicable, for example ‘ subtopic ‘- full link for example (www.example.com).
                         
                         The report should be structured as follows:
                         
                         1. Cost Optimization Analysis (Must be displayed in a table, always use estimated numbers). Also always build table based on this information: [Cost Component: Matirials, Labor, Permitting, Excavation and restoration, total. New Deployment in euro, Using existing canalization in euro, cost reductions in euro, cost reduction in percentage, time reduction in percentage]
                         
                         Construction & Operational Costs: Provide a breakdown of costs, including materials, labor, permitting, and logistics expenses .
                         Cost-Saving Benefits of Connecting to Existing Canalization: Compare the costs of new trenching and installation versus using the existing infrastructure.
                         Projected Savings: Estimate savings in terms of excavation, labor, and installation time by leveraging the unused pipelineDTO.
                         Implementation Timeframe: Provide an estimate of how much faster the project can be completed using the existing empty pipelineDTO.
                         Data Presentation: Show cost and time-related data in a detailed table for clarity.
                         2. Regional Connectivity Improvements
                          Identify businesses or industries that will benefit from the improved connectivity based on  the address  provided by the user the radius of 7000 meters . Identify how it will ensure long-terms scalability. Identify local infrastructure that could facilitate this deployment.
                         3. Regulatory Compliance & Environmental Considerations
                         Provide full applicable regulations in address provided by the user. Identify potential risks and still prevention strategies. List relevant authorities, required permits and full details for local regulatory bodies in address provided by the user. You must provide officials resources and references for each regulation and compliance in this format – www.example.com, so don’t put text as a link, this is very important!
                         4. Infrastructure Installation Strategy
                         Installation Approach: Explain trenching, welding, hydrostatic testing, and other essential construction techniques. Assess technical viability, challenges, and necessary adaptations to ensure successful implementation.
                         The analysis must be clear, precise, and actionable, with realistic financial and technical estimates. Ensure all data is specific to networking pipelineDTOS."
                         
                        """)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        new PromptChatMemoryAdvisor(chatMemory),
                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().topK(3).similarityThreshold(0.75).build())
                )
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
}

