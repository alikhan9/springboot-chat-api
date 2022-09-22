version : "3.9"
services:
  backend:
    image: "alikhanzaipoulaiev/chatapp:lastest"
    ports:
      - "80:8081"
    restart: "always"