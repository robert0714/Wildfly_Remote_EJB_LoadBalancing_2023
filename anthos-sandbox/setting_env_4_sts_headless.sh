#!/bin/bash
 
echo "======================================="
echo "Startup Minikube"

minikube -p sandbox delete && minikube -p sandbox start --kubernetes-version=v1.24.13 \
--cpus=4 \
--memory=8g \
--bootstrapper=kubeadm \
--extra-config=kubelet.authentication-token-webhook=true \
--extra-config=kubelet.authorization-mode=Webhook \
--extra-config=scheduler.bind-address=0.0.0.0 \
--extra-config=controller-manager.bind-address=0.0.0.0 \
--extra-config=kubeadm.pod-network-cidr=192.168.0.0/16

BASE=$(pwd)

echo "======================================="
echo "Deploying Server Side"
kubectl apply -f  "$BASE/ejblb-headless-svc-sts.yaml"

echo "======================================="
echo "Deploying Client Side"

kubectl apply -f  "$BASE/ejbclient-headless-svc-sts.yaml" 