# ##############################################################################
# Jump to the shared drive
# ##############################################################################

# East:
#   gcloud container clusters get-credentials engin-dev --zone us-east4-a --project r2ai-266621
# Central:
#   gcloud container clusters get-credentials engin-dev --zone us-central1-c  --project r2ai-266621
#   kubectl exec -it -n db-backup controller-6864576f74-wbpd7 -- sh
#
gcloud container clusters get-credentials engin-dev --zone us-east4-a  --project r2ai-266621
kubectl --context gke_r2ai-266621_us-east4-a_engin-dev -n trireme-perf exec -it confluent-tools -- bash 

