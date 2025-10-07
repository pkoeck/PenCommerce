## Summary

Describe the changes in this pull request.

## Type of change

- [ ] Bug fix
- [ ] Feature
- [ ] Chore / Refactor
- [ ] Docs

## How has this been tested?

- [ ] I ran tests for the affected module(s)
  - Windows PowerShell examples:
    - Build all: `./mvnw -q verify`
    - Catalog only: `./mvnw -q -pl services/catalog-service -am test`
    - Order only: `./mvnw -q -pl services/order-service -am test`
- [ ] CI passes (GitHub Actions)

## Checklist

- [ ] I updated documentation if needed (e.g., HELP.md)
- [ ] I followed the code style and module independence guidelines
- [ ] No new failing tests were introduced

## Related issues

Link to any related issues (e.g., closes #123).
