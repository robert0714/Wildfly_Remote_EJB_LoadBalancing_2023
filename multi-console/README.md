* Reference: https://hal.github.io/documentation/get-started/#container

```
docker run -itd -p 9090:9090  --name console quay.io/halconsole/hal:3.5.12.Final

docker run -itd  --name console --network host  quay.io/halconsole/hal:3.5.12.Final
```

