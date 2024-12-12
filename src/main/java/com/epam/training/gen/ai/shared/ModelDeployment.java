package com.epam.training.gen.ai.shared;

public enum ModelDeployment {
  DALL_E_3("dall-e-3"),
  GPT_35_TURBO_0125("gpt-35-turbo-0125"),
  GPT_35_TURBO_0301("gpt-35-turbo-0301"),
  GPT_35_TURBO_0613("gpt-35-turbo-0613"),
  GPT_35_TURBO_1106("gpt-35-turbo-1106"),
  GPT_35_TURBO_16_K("gpt-35-turbo-16k"),
  GPT_35_TURBO("gpt-35-turbo"),
  GPT_4_0125_PREVIEW("gpt-4-0125-preview"),
  GPT_4_0613("gpt-4-0613"),
  GPT_4_1106_PREVIEW("gpt-4-1106-preview"),
  GPT_4_32_K_0314("gpt-4-32k-0314"),
  GPT_4_32_K_0613("gpt-4-32k-0613"),
  GPT_4_32_K("gpt-4-32k"),
  GPT_4("gpt-4"),
  GPT_4_TURBO_2024_04_09("gpt-4-turbo-2024-04-09"),
  GPT_4_TURBO("gpt-4-turbo"),
  GPT_4_VISION_PREVIEW("gpt-4-vision-preview"),
  GPT_4_O_2024_05_13("gpt-4o-2024-05-13"),
  GPT_4_O_2024_08_06("gpt-4o-2024-08-06"),
  GPT_4_O("gpt-4o"),
  GPT_4_O_MINI_2024_07_18("gpt-4o-mini-2024-07-18"),
  TEXT_EMBEDDING_ADA_002("text-embedding-ada-002"),
  TEXT_EMBEDDING_3_SMALL_1("text-embedding-3-small-1"),
  TEXT_EMBEDDING_3_LARGE_1("text-embedding-3-large-1"),
  AMAZON_TITAN_TG_1_LARGE("amazon.titan-tg1-large"),
  AI_21_J_2_GRANDE_INSTRUCT("ai21.j2-grande-instruct"),
  AI_21_J_2_JUMBO_INSTRUCT("ai21.j2-jumbo-instruct"),
  ANTHROPIC_CLAUDE("anthropic.claude"),
  ANTHROPIC_CLAUDE_INSTANT_V_1("anthropic.claude-instant-v1"),
  ANTHROPIC_CLAUDE_V_2("anthropic.claude-v2"),
  ANTHROPIC_CLAUDE_V_2_1("anthropic.claude-v2-1"),
  ANTHROPIC_CLAUDE_V_3_OPUS("anthropic.claude-v3-opus"),
  ANTHROPIC_CLAUDE_V_3_SONNET("anthropic.claude-v3-sonnet"),
  ANTHROPIC_CLAUDE_V_3_5_SONNET("anthropic.claude-v3-5-sonnet"),
  ANTHROPIC_CLAUDE_V_3_HAIKU("anthropic.claude-v3-haiku"),
  STABILITY_STABLE_DIFFUSION_XL("stability.stable-diffusion-xl"),
  LLAMA_3_8_B_INSTRUCT("Llama-3-8B-Instruct"),
  LLAMA_3_70_B_INSTRUCT_AWQ("llama-3-70b-instruct-awq"),
  MISTRAL_7_B_INSTRUCT("Mistral-7B-Instruct"),
  MIXTRAL_8_X_7_B_INSTRUCT_V_0_1("Mixtral-8x7B-Instruct-v0.1"),
  CODE_LLAMA_34_B_INSTRUCT_HF("CodeLlama-34b-Instruct-hf"),
  CHAT_BISON_001("chat-bison@001"),
  CHAT_BISON_32_K_002("chat-bison-32k@002"),
  CODECHAT_BISON_001("codechat-bison@001"),
  CODECHAT_BISON_32_K_002("codechat-bison-32k@002"),
  GEMINI_PRO("gemini-pro"),
  GEMINI_PRO_VISION("gemini-pro-vision"),
  GEMINI_1_5_PRO_PREVIEW_0409("gemini-1.5-pro-preview-0409"),
  GEMINI_1_5_FLASH_001("gemini-1.5-flash-001"),
  IMAGEGENERATION_005("imagegeneration@005"),
  TEXTEMBEDDING_GECKO_001("textembedding-gecko@001"),
  O_1_MINI_2024_09_12("o1-mini-2024-09-12"),
  O_1_PREVIEW_2024_09_12("o1-preview-2024-09-12");

  private final String deploymentId;

  ModelDeployment(String deploymentId) {
    this.deploymentId = deploymentId;
  }

  public String getDeploymentId() {
    return this.deploymentId;
  }
}
