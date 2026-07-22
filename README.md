## Git Flow

### Branch Naming
```
feature/{feature-name}   - feature/order-tracking
fix/{bug-description}    - fix/dynamodb-connection-timeout
refactor/{scope}         - refactor/order-mapper-cleanup
chore/{task-description} - chore/update-dependencies
docs/{doc-topic}         - docs/readme-setup
test/{test-scope}        - test/order-service-integration-tests
ci/{pipeline-task}       - ci/github-actions-cache
infra/{infrastructure}   - infra/terraform-vpc
perf/{optimization}      - perf/reduce-query-latency
```
- kebab-case, lowercase, no spaces, no special characters
- keep it short but descriptive (3-5 words max)

### Commit Messages
Following [Conventional Commits](https://www.conventionalcommits.org/):
```
<type>(<optional scope>): <short summary> 

Example: chore(deps): update spring-boot to 4.1.0
```
**Types:** `feat`, `fix`, `refactor`, `chore`, `docs`, `test`, `perf`, `style`, `build`, `ci`
- one commit = one logical change
- short  but descriptive сommit message with scope
- scope = the module/domain the change touches

### Pull Request Titles
```
<Type>: <short summary> 

Example: Feat: add order tracking by trackingId
```
- Same type as commits
- capitalized, no trailing period.
