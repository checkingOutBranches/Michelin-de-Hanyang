FROM node:16-alpine as build

ARG REACT_APP_API

WORKDIR /app

COPY package*.json ./

RUN yarn install --network-timeout 1000000

COPY . .

<<<<<<< HEAD
RUN yarn build
=======
RUN REACT_APP_API=$REACT_APP_API npm run build
>>>>>>> BEDev

FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*

COPY --from=build /app/build /usr/share/nginx/html

COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]