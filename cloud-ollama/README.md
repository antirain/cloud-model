# Cloud Ollama 服务

基于Spring Boot和LangChain4j的Ollama AI模型服务，提供免费的本地AI能力。

## 功能特性

- ✅ 支持多种开源AI模型（Llama2、Mistral、DeepSeek等）
- ✅ 普通聊天和流式聊天
- ✅ 微服务架构，支持Nacos注册中心
- ✅ 完全免费，本地运行
- ✅ WebSocket流式响应支持

## 快速开始

### 1. 安装Ollama

```bash
# Windows
iwr https://ollama.ai/install.ps1 -useb | iex

# Linux/Mac
curl -fsSL https://ollama.ai/install.sh | sh
```

### 2. 下载模型

```bash
# 下载Llama2 7B模型
ollama pull llama2:7b

# 查看已安装模型
ollama list
```

### 3. 启动Ollama服务

```bash
# 启动Ollama服务（默认端口11434）
ollama serve
```

### 4. 启动Cloud Ollama服务

```bash
# 编译项目
mvn clean package

# 启动服务
java -jar target/cloud-ollama-1.0.0.jar
```

## API接口

### 普通聊天

```http
POST /api/chat/completion
Content-Type: application/json

{
  "message": "用Java写一个快速排序算法",
  "model": "llama2:7b",
  "temperature": 0.7
}
```

### 流式聊天

```http
POST /api/chat/stream
Content-Type: application/json

{
  "message": "介绍一下Spring Boot",
  "model": "llama2:7b"
}
```

### 获取可用模型

```http
GET /api/chat/models
```

## 配置说明

### application.yml

```yaml
ollama:
  base-url: http://localhost:11434  # Ollama服务地址
  model-name: llama2:7b            # 默认模型
```

### 支持的模型

- `llama2:7b` - Meta Llama2 7B参数
- `llama2:13b` - Meta Llama2 13B参数  
- `mistral:7b` - Mistral 7B模型
- `codellama:7b` - 代码专用模型
- `deepseek-coder:6.7b` - DeepSeek代码模型
- `qwen:7b` - 通义千问中文模型

## 开发说明

### 项目结构

```
cloud-ollama/
├── src/main/java/com/cloud/ollama/
│   ├── OllamaApplication.java     # 启动类
│   ├── config/                    # 配置类
│   ├── controller/               # 控制器
│   ├── dto/                      # 数据传输对象
│   └── service/                  # 服务层
└── src/main/resources/
    └── application.yml           # 配置文件
```

### 依赖说明

- `langchain4j` - AI框架核心
- `langchain4j-ollama` - Ollama集成
- `spring-boot-starter-websocket` - WebSocket支持
- `cloud-common-web` - 公共Web模块

## 注意事项

1. **内存要求**：7B模型需要至少8GB内存，13B模型需要16GB内存
2. **性能优化**：有GPU可以显著提升推理速度
3. **模型选择**：根据任务类型选择合适的模型（代码/对话/推理）
4. **网络要求**：确保Ollama服务可访问（默认localhost:11434）