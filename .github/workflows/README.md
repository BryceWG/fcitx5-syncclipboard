# GitHub Actions：自动打包并发布 Release

本仓库包含一个自动发布 workflow：当你在 `master`（或 `main`）分支提交并推送 **版本号变更**（`app/build.gradle.kts` 内的 `versionName`）时，会自动构建 **签名后的 Release APK**，并创建对应的 GitHub Release（tag 形如 `v0.1.0`），同时上传 APK 和其 `sha256` 校验文件。

## 需要配置的 Secrets

在 GitHub 仓库 `Settings` -> `Secrets and variables` -> `Actions` 中配置以下 Secrets：

| Secret 名称 | 说明 |
|------------|------|
| `SIGNING_KEYSTORE_BASE64` | 签名文件 (.jks/.keystore) 的 Base64 编码 |
| `SIGNING_KEY_ALIAS` | 密钥别名 |
| `SIGNING_KEY_PASSWORD` | 密钥密码 |
| `SIGNING_STORE_PASSWORD` | 密钥库密码 |

## 如何触发发布

1. 修改 `app/build.gradle.kts` 里的 `versionName` / `versionCode`（至少需要变更 `versionName`）。
2. 推送到 `master`（或 `main`）分支。
3. workflow `.github/workflows/release.yml` 会检测到版本号变化并执行发布。

也可以在 GitHub Actions 页面手动运行 `Release (APK)`（`workflow_dispatch`）。
