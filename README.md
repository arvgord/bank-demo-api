# gRPC API generation and publishing with GitHub Actions and GitHub Packages

![build](https://github.com/arvgord/bank-demo-api/actions/workflows/build.yml/badge.svg)

This project demonstrates the use of GitHub Actions to automate the processes of generating
and publishing gRPC API packages, integrating [Protolint](https://github.com/yoheimuta/protolint)
and [Protolock](https://github.com/nilslice/protolock). The project focuses on simplifying the
development of gRPC APIs for services in Kotlin/Java and gRPC-web clients, with package publishing
to Maven and npm registries in GitHub Packages.

## Features
- **Automation**: The build and publication of gRPC packages are fully automated through GitHub Actions.
- **Artifact Generation**: Demonstrates the generation of gRPC API artifacts for deployment in Maven
  and npm registries within GitHub Packages.
- **Envoy Proxy**: Configured examples of envoy proxy are provided, enabling gRPC-web clients to
  interact with gRPC services.
- **Code Style**: [Protolint](https://github.com/yoheimuta/protolint) is integrated to ensure .proto
  files maintain a consistent style, guaranteeing code consistency and readability.
- **Backward Compatibility**: [Protolock](https://github.com/nilslice/protolock) is used to maintain
  the backward compatibility of the API, controlling changes in .proto files and preventing incompatible
  modifications.

## Workflow
1. Set up GitHub Actions for building and publishing packages.
2. Use GitHub Packages to store and distribute artifacts.
3. Apply Protolint and Protolock to ensure the quality and stability of the API.

For detailed information on setting up and using Protolint and Protolock, please refer to the
documentation in the respective repositories on GitHub.

For a practical example of this API in use, please see the [bank-demo project](https://github.com/arvgord/bank-demo).