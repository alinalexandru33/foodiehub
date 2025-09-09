## Setup

### Prerequisites
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### Start dependencies

Lansează containerele MongoDB și Redis:

```bash
docker run -d --name mongo -p 27017:27017 mongo:6
docker run -d --name redis -p 6379:6379 redis:7
