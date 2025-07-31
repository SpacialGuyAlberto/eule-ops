{{/* nombre release-safe */}}
{{- define "kafka-shared.fullname" -}}
{{- printf "%s-%s" .Release.Name .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}

{{/* etiquetas comunes */}}
{{- define "kafka-shared.labels" -}}
app.kubernetes.io/name: {{ include "kafka-shared.fullname" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/component: kafka
{{- end }}
