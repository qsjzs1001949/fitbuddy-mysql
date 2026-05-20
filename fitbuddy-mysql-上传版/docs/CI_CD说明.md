# CI/CD 说明

本项目使用 GitHub Actions 做一套简单的 CI/CD 展示流程，配置文件位于：

`.github/workflows/ci.yml`

## 触发方式

当代码发生以下操作时，流程会自动运行：

- 向 GitHub 仓库 push 代码
- 创建或更新 pull request

## 运行内容

流程会在 GitHub 提供的 Ubuntu 环境中执行以下步骤：

1. 拉取项目源代码。
2. 安装 JDK 8，与 `build.gradle` 中的 `sourceCompatibility = JavaVersion.VERSION_1_8` 和 `targetCompatibility = JavaVersion.VERSION_1_8` 保持一致。
3. 使用项目自带的 Gradle Wrapper 自动下载 Gradle 和项目依赖。
4. 检查 `src/test` 目录下是否存在测试文件。
5. 如果存在测试文件，执行测试命令：

   ```bash
   ./gradlew test --no-daemon
   ```

6. 如果没有测试文件，则执行最简单的编译检查，避免因为测试为空影响流程展示：

   ```bash
   ./gradlew classes --no-daemon
   ```

7. 执行 Spring Boot 打包检查。当前项目使用了 `org.springframework.boot` 插件，因此存在 `bootJar` 任务：

   ```bash
   ./gradlew bootJar --no-daemon
   ```

8. 如果项目根目录存在 `Dockerfile`，会额外执行 Docker 镜像构建检查：

   ```bash
   docker build -t fitbuddy-mysql-demo:ci .
   ```

当前项目没有检测到 `Dockerfile`，所以 Docker 构建步骤会自动跳过。以后如果添加了 `Dockerfile`，该步骤会自动启用。

## 查看运行结果

上传代码到 GitHub 后，可以在仓库页面查看结果：

1. 打开 GitHub 仓库。
2. 点击顶部的 **Actions**。
3. 选择名为 **CI/CD Demo** 的工作流。
4. 点击某一次运行记录，可以查看每个步骤的日志和结果。

如果测试或构建失败，GitHub Actions 会显示红色失败标记；如果全部通过，会显示绿色通过标记。

## 安全说明

这套流程只用于课程作业展示，不连接真实服务器，也不使用账号密码、token 或 SSH 密钥。配置文件中没有写入任何敏感信息。

## 本地验证说明

本地执行 Gradle Wrapper 时，因为需要从网络下载 Gradle 发行包，当前环境出现过下载超时。这属于本地网络环境问题，不代表 CI/CD 配置不可用。代码 push 到 GitHub 后，GitHub Actions 会在云端环境重新下载 Gradle 和项目依赖，并重新执行测试、编译、打包等验证步骤。
