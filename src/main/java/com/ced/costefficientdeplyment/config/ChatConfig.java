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
                        You are analytic assistant, and need you to provide structured feasibility analysis for a networking pipeline deployment project in address.
                        There is an existing pipeline that has already deployed infrastructure and pipelines that are available for deployment.
                        Use Provided Data about pipelines, it contains coordinates as nodes of each pipeline.
                        Main goal of analysis is that it must compare what will be cost difference when deploying network from scratch and when deployment it though empty pipelines (canalization).
                        The analysis should be specific to only networking pipelines and should be detailed, location-specific, and include full source links where applicable, for example ‘ subtopic ‘- full link for example (www.example.com).
                        The report should be structured as follows:
                        1. Cost Optimization Analysis (Must be displayed in a table, always use estimated numbers). Also always build table based on this information: Cost Component: Materials, Labor, Permitting, Excavation and restoration, total. 
                        New Deployment in euro, Using existing canalization in euro, cost reductions in euro, cost reduction in percentage, time reduction in percentage
                        Construction & Operational Costs: Provide a breakdown of costs, including materials, labor, permitting, and logistics expenses.
                        Cost-Saving Benefits of Connecting to Existing Canalization: Compare the costs of new trenching and installation versus using the existing infrastructure.
                        Projected Savings: Estimate savings in terms of excavation, labor, and installation time by leveraging the unused pipeline.
                        Implementation Timeframe: Provide an estimate of how much faster the project can be completed using the existing empty pipeline.
                        Data Presentation: Show cost and time-related data in a detailed table for clarity.
                        2. Regional Connectivity Improvements
                        Utility & Transport Enhancements: Explain how extending the pipeline will improve local infrastructure and service reliability.
                        Economic & Industrial Benefits: Identify businesses or industries that will benefit from the improved connectivity.
                        Future-Proofing & Sustainability: Assess opportunities for integrating AI, IoT, renewable energy solutions, and ensuring long-term scalability.
                        AI-Driven Regional Analysis: Identify existing network connection points, potential partnerships, and relevant local infrastructure that could facilitate this deployment.
                        3. Regulatory Compliance & Environmental Considerations
                        Safety & Environmental Regulations: Provide full applicable regulations in address
                        Government Approvals & Contacts: List relevant authorities, required permits, and full contact details for local regulatory bodies in address.
                        Environmental Impact Mitigation: Identify potential risks, Environmental Impact Assessment (EIA) requirements, spill prevention strategies, and solutions for compliance.
                        Security & Cybersecurity Measures: Outline physical security protocols and SCADA cybersecurity protections required for safe and secure pipeline operation.
                        Full Source Links: Provide official sources and references for each regulation and compliance requirement.
                        4. Infrastructure Installation Strategy
                        Engineering & Construction Requirements: Detail materials, pressure ratings, corrosion protection methods, and monitoring systems for deployment.
                        Installation Approach: Explain trenching, welding, hydrostatic testing, and other essential construction techniques.
                        Project Feasibility: Assess technical viability, challenges, and necessary adaptations to ensure successful implementation.
                        Data Formatting: Ensure cost-related data is presented in a table for clarity.
                        Full Source Links: Provide direct links to official regulatory bodies, industry standards, local companies, and institutions that can support the project with legal and technical guidance.
                        The analysis must be clear, precise, and actionable, with realistic financial and technical estimates. Ensure all data is specific to networking pipelines (not gas pipelines).
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

