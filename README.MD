# Hacker-News

Restful-API for handling posts creation, updating and voting
## Running

```bash
docker-compose up -d
```

## Tech Stack
- Main lang: Clojure
- Server: jetty with ring-adapter and compojure-API
- DB access: toucan
- DB: PostgreSQL
- Build: docker and docker-compose

## Usage
|   what it does  	| method 	| parm type 	|                  parm example                 	|                  endpoint                 	|   	|
|:---------------:	|:------:	|:---------:	|:---------------------------------------------:	|:-----------------------------------------:	|:-:	|
|       find      	|   GET  	| path-parm 	|                   path-parm                   	|    http://127.0.0.1:3000/api/post/:{id}   	|   	|
|      create     	|  POST  	| json-body 	| {"author": "Your name",  "text": "Your Text"} 	|      http://127.0.0.1:3000/api/posts      	|   	|
|      update     	|   PUT  	| json-body 	|        {"id": 8,  "text": "Your Text"}        	|      http://127.0.0.1:3000/api/posts      	|   	|
|     up-vote     	|  POST  	| json-body 	|                   {"id": 8}                   	|   http://127.0.0.1:3000/api/posts/upvote  	|   	|
|    down-vote    	|  POST  	| json-body 	|                   {"id": 8}                   	|  http://127.0.0.1:3000/api/posts/downvote 	|   	|
| fetch top-posts 	|   GET  	|    NONE   	|                                               	| http://localhost:3000/api/posts/top-posts 	|   	|

## Swagger UI
Availble at http://localhost:3000/swagger/index.html

## REPL
- available at port 12345