# StartUp
```
./setting_env.sh
```

# Dashboard
```
minikube -p sandbox dashboard
```
# Grafana
```
kubectl -n monitoring port-forward svc/grafana 3001:3000
```

# EJB-Client  Test
* Use kubectl port-forward: 
```
kubectl  port-forward svc/ejblbclient-service  8081:8080
```
* Use Anthos Sandbox 's Web Preview
  * path: ``/test/stateless``
    ```
    https://8081-cs-983647568396-default.cs-asia-east1-jnrc.cloudshell.dev/test/stateless?authuser=0&redirectedPreviously=true
    ```


```
podNames=$(kubectl get pods -l application=ejblb  --no-headers -o custom-columns=":metadata.name")

# kubectl label pod <your-pod-name> version=v1
# use loop,counter
counter=0;

for i in $podNames ;\
do \
  echo $i; \
  echo "---------------------------------"  ;\
  let counter=counter+1 ;
  kubectl label pod $i test=v$counter ;
done

kubectl get pods -l application=ejblb --show-labels 

NAME                               READY   STATUS    RESTARTS   AGE   LABELS
ejblb-deploy-577b4477c6-rvjgv      2/2     Running   0          16h   application=ejblb,istio.io/rev=basic,pod-template-hash=577b4477c6,security.istio.io/tlsMode=istio,service.istio.io/canonical-name=ejblb-deploy,service.istio.io/canonical-revision=latest,test=v25
ejblb-deploy-v1-855667c655-twfwt   2/2     Running   0          16h   app=ejblb-v1,application=ejblb,istio.io/rev=basic,pod-template-hash=855667c655,security.istio.io/tlsMode=istio,service.istio.io/canonical-name=ejblb-v1,service.istio.io/canonical-revision=v1,test=v26,version=v1
ejblb-deploy-v2-6ff8fc74df-9xvjc   2/2     Running   0          16h   app=ejblb-v2,application=ejblb,istio.io/rev=basic,pod-template-hash=6ff8fc74df,security.istio.io/tlsMode=istio,service.istio.io/canonical-name=ejblb-v2,service.istio.io/canonical-revision=v2,test=v27,version=v2
ejblb-deploy-v3-75fd47b6ff-7tbhv   2/2     Running   0          16h   app=ejblb-v3,application=ejblb,istio.io/rev=basic,pod-template-hash=75fd47b6ff,security.istio.io/tlsMode=istio,service.istio.io/canonical-name=ejblb-v3,service.istio.io/canonical-revision=v3,test=v28,version=v3
ejblb-deploy-v4-6c9db49cc7-97zkx   2/2     Running   0          16h   app=ejblb-v4,application=ejblb,istio.io/rev=basic,pod-template-hash=6c9db49cc7,security.istio.io/tlsMode=istio,service.istio.io/canonical-name=ejblb-v4,service.istio.io/canonical-revision=v4,test=v29,version=v4
ejblb-deploy-v5-5dfccc9687-xxpjm   2/2     Running   0          16h   app=ejblb-v5,application=ejblb,istio.io/rev=basic,pod-template-hash=5dfccc9687,security.istio.io/tlsMode=istio,service.istio.io/canonical-name=ejblb-v5,service.istio.io/canonical-revision=v5,test=v30,version=v5

```    