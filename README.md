# 面试刷题平台 (Mianshiliu-Next)

<div align="center">

![logo](mianshiliu-next-frontend/public/assets/logo.png)

**一个现代化的在线面试刷题学习平台**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.2-green.svg)](https://spring.io/projects/spring-boot)
[![Next.js](https://img.shields.io/badge/Next.js-14.2.6-black.svg)](https://nextjs.org/)
[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.x-blue.svg)](https://www.typescriptlang.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

</div>

---

## 📖 项目简介

面试刷题平台是一个前后端分离的全栈项目，旨在帮助用户系统性地学习和准备技术面试。用户可以浏览题库、刷题、查看答案解析，管理员可以管理题库和题目内容。

## 🛠️ 技术栈

### 后端技术

| 技术 | 说明 | 版本 |
|------|------|------|
| Spring Boot | 核心框架 | 2.7.2 |
| MyBatis Plus | ORM 框架 | 3.5.2 |
| MySQL | 关系型数据库 | 8.x |
| Redis | 缓存数据库 | 6.x |
| Elasticsearch | 全文搜索引擎 | 7.x |
| Sa-Token | 权限认证框架 | 1.39.0 |
| Sentinel | 流量控制组件 | 1.8.6 |
| Druid | 数据库连接池 | 1.2.23 |
| Knife4j | API 文档 | 4.4.0 |
| Nacos | 配置中心 | - |
| Redisson | Redis 客户端 | 3.21.0 |

### 前端技术

| 技术 | 说明 | 版本 |
|------|------|------|
| Next.js | React 框架 | 14.2.6 |
| React | 前端框架 | 18.x |
| TypeScript | 类型安全 | 5.x |
| Ant Design | UI 组件库 | 5.22.1 |
| Ant Design Pro Components | 高级组件 | 2.8.2 |
| Redux Toolkit | 状态管理 | 2.3.0 |
| Axios | HTTP 客户端 | 1.7.7 |
| ECharts | 数据可视化 | 5.5.1 |
| ByteMD | Markdown 编辑器 | 1.21.0 |

## ✨ 功能特性

### 用户端功能

- 🔐 **用户系统**：注册、登录、注销、微信登录
- 📚 **题库浏览**：分类查看题库，支持分页
- 📝 **题目刷题**：查看题目详情、答案解析
- 🔍 **搜索功能**：支持关键词搜索题目
- ✅ **每日签到**：基于 Redis BitMap 实现

### 管理端功能

- 👥 **用户管理**：用户列表、封禁用户
- 📂 **题库管理**：题库增删改查
- 📋 **题目管理**：题目增删改查、批量操作

### 系统特性

- 🛡️ **安全防护**：爬虫检测、IP 黑名单、权限控制
- ⚡ **性能优化**：Sentinel 限流降级、Redis 缓存
- 📊 **监控管理**：Druid 监控面板、请求日志
- 🐳 **容器化**：支持 Docker 部署

## 📁 项目结构

```
mianshiliu-next/
├── mianshiliu-next-backend/          # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/yupi/mianshiliu/
│   │   │   │   ├── annotation/       # 自定义注解
│   │   │   │   ├── aop/              # AOP 切面
│   │   │   │   ├── blackfilter/      # IP 黑名单过滤
│   │   │   │   ├── common/           # 通用类
│   │   │   │   ├── config/           # 配置类
│   │   │   │   ├── constant/         # 常量
│   │   │   │   ├── controller/       # 控制器
│   │   │   │   ├── exception/        # 异常处理
│   │   │   │   ├── mapper/           # 数据访问层
│   │   │   │   ├── model/            # 数据模型
│   │   │   │   ├── service/          # 服务层
│   │   │   │   └── utils/            # 工具类
│   │   │   └── resources/            # 配置文件
│   │   └── test/                     # 单元测试
│   ├── sql/                          # SQL 脚本
│   ├── Dockerfile                    # Docker 构建文件
│   └── pom.xml                       # Maven 配置
│
└── mianshiliu-next-frontend/         # 前端项目
    ├── src/
    │   ├── access/                   # 权限控制
    │   ├── api/                      # API 接口
    │   ├── app/                      # 页面路由
    │   ├── components/               # 公共组件
    │   ├── layouts/                  # 布局组件
    │   └── stores/                   # Redux 状态
    ├── config/                       # 配置文件
    ├── public/                       # 静态资源
    └── package.json                  # npm 配置
```

## 🚀 快速开始

### 环境要求

- JDK 8+
- Node.js 18+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+

### 后端启动

1. **克隆项目**
```bash
git clone https://github.com/ccc6yj/mianshiliu-next.git
cd mianshiliu-next
```

2. **初始化数据库**
```bash
# 执行 SQL 脚本
mysql -u root -p < mianshiliu-next-backend/sql/create_table.sql
mysql -u root -p mianshiliu < mianshiliu-next-backend/sql/init_data.sql
```

3. **修改配置**
```yaml
# 编辑 mianshiliu-next-backend/src/main/resources/application.yml
# 修改数据库、Redis、Elasticsearch 等配置
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mianshiliu
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379
```

4. **启动后端**
```bash
cd mianshiliu-next-backend
mvn spring-boot:run
```

5. **访问接口文档**
```
http://localhost:8101/api/doc.html
```

### 前端启动

1. **安装依赖**
```bash
cd mianshiliu-next-frontend
npm install
```

2. **启动开发服务器**
```bash
npm run dev
```

3. **访问前端**
```
http://localhost:3000
```

## 🐳 Docker 部署

### 后端 Docker 构建

```bash
cd mianshiliu-next-backend
docker build -t mianshiliu-backend .
docker run -d -p 8101:8101 --name mianshiliu-backend mianshiliu-backend
```

## 📊 数据库设计

### 核心表结构

| 表名 | 说明 |
|------|------|
| user | 用户表 |
| question_bank | 题库表 |
| question | 题目表 |
| question_bank_question | 题库题目关联表 |

详细表结构请参考 `mianshiliu-next-backend/sql/create_table.sql`

## 🔐 默认账号

| 角色 | 账号 | 密码 |
|------|------|------|
| 管理员 | admin | 12345678 |
| 普通用户 | user | 12345678 |

> ⚠️ 请在生产环境中修改默认密码

## 📝 开发规范

### 后端规范
- 统一返回结果：`BaseResponse<T>`
- 统一异常处理：`GlobalExceptionHandler`
- 接口权限控制：`@SaCheckRole` 注解
- 请求日志记录：AOP 切面 `LogInterceptor`

### 前端规范
- 统一权限校验：`AccessLayout` 组件
- 状态管理：Redux Toolkit
- API 生成：OpenAPI 自动生成

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 📄 开源协议

本项目采用 MIT 协议开源，详情请参阅 [LICENSE](LICENSE) 文件。

## 🙏 致谢

- [Ant Design](https://ant.design) - 优秀的 UI 组件库
- [Spring Boot](https://spring.io/projects/spring-boot) - 强大的后端框架

---

<div align="center">

**如果这个项目对你有帮助，请给一个 ⭐ Star 支持一下！**

Made with ❤️ by [ccc6yj](https://github.com/ccc6yj)

</div>
