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
                        "I need a structured feasibility analysis for a networking pipeline deployment project in the address provided in the user prompt.
                         
                         There is an existing pipeline provided in the user prompt and an unused pipeline (canalization) provided away in the prompt, each with its own length. The main goal is to compare the cost difference when deploying the network from scratch versus deploying it through empty pipelines (canalizations).
                         
                         The analysis should be specific to networking pipelines and must be detailed, location-specific, and include full source links (always providing the complete URL including the protocol, e.g., https://www.example.com) where applicable.
                         
                         **Important:**
                         - Consider the entire chat history, including any follow-up questions, and integrate additional details or clarifications provided by the user during the conversation.
                         - If the user asks follow-up questions (e.g., "how can it improve connectivity?"), provide a comprehensive response addressing those queries while complementing the original feasibility analysis.
                         
                         The report should be structured as follows:
                         
                         1. **Cost Optimization Analysis**
                            - Provide a breakdown of construction and operational costs (materials, labor, permitting, logistics, etc.). \s
                            - Compare the cost-saving benefits of connecting to an existing canalization versus new trenching and installation. \s
                            - Estimate projected savings (excavation, labor, installation time) and provide an estimated implementation timeframe. \s
                            - **Data Presentation:** Return the cost and time-related data as a JSON object only (in a code block). The JSON must have the following structure (and no extra text):
                             json
                                costData: 
                                    "component": "Materials",
                                    "newDeployment": <number>,
                                    "existingCanalization": <number>,
                                    "costReduction": <number>,
                                    "costReductionPercent": <number>,
                                    "timeReduction": <number>
                                  
                                    "component": "Labor",
                                    "newDeployment": <number>,
                                    "existingCanalization": <number>,
                                    "costReduction": <number>,
                                    "costReductionPercent": <number>,
                                    "timeReduction": <number>
                                  
                                    "component": "Permitting",
                                    "newDeployment": <number>,
                                    "existingCanalization": <number>,
                                    "costReduction": <number>,
                                    "costReductionPercent": <number>,
                                    "timeReduction": <number>
                                  
                                    "component": "Excavation and Restoration",
                                    "newDeployment": <number>,
                                    "existingCanalization": <number>,
                                    "costReduction": <number>,
                                    "costReductionPercent": <number>,
                                    "timeReduction": <number>
                                  
                                "totals": 
                                  "newDeployment": <number>,
                                  "existingCanalization": <number>,
                                  "costReduction": <number>,
                                  "costReductionPercent": <number>,
                                  "timeReduction": <number>
                                  
                                "regulatoryLinks": 
                                  
                                    "id": 1,
                                    "title": "Title of regulation 1",
                                    "link": "https://www.example.com"
                                    
                                    "id": 2,
                                    "title": "Title of regulation 2",
                                    "link": "https://www.example.com"

                                    "id": 3,
                                    "title": "Title of regulation 3",
                                    "link": "https://www.example.com"
                                    
                         2. **Regional Connectivity Improvements**
                            - Identify businesses or industries within a 7000-meter radius of the provided address that will benefit from improved connectivity.
                            - Explain how the network ensures long-term scalability and list local infrastructure that could facilitate deployment.
                         
                         3. **Regulatory Compliance & Environmental Considerations**
                            - List all applicable regulations for the address, including potential risks and prevention strategies.
                            - Provide at least three regulatory links (with full URLs) for local regulatory bodies or official resources.
                         
                         4. **Infrastructure Installation Strategy**
                            - Explain the installation approach (trenching, welding, hydrostatic testing, etc.), assess technical viability, and discuss necessary adaptations.
                         
                         Ensure the analysis is clear, precise, and actionable with realistic financial and technical estimates, and that all data is specific to networking pipelines.
                        """)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        new PromptChatMemoryAdvisor(chatMemory)
                )
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
}

