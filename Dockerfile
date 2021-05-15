FROM nginx
RUN mkdir /app
COPY /dist /app
COPY nginx.conf /usr/local/nginx/conf
