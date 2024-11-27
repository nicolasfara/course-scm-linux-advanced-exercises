FROM alpine:latest

RUN apk add --no-cache nginx

COPY nginx.conf /etc/nginx/nginx.conf
COPY index.html /var/lib/nginx/index.html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
