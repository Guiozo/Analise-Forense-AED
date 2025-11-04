package br.edu.icev.aed.forense;

/**
 * Classe que representa um alerta de segurança extraído dos logs.
 * Contém todas as informações de uma linha do arquivo de log.
 */
public class Alerta {
    private long timestamp;
    private String userId;
    private String sessionId;
    private String actionType;
    private String targetResource;
    private int severityLevel;
    private long bytesTransferred;
    
    /**
     * Construtor para criar um alerta a partir dos dados do log.
     * 
     * @param timestamp Unix timestamp da ação
     * @param userId ID do usuário
     * @param sessionId ID da sessão
     * @param actionType Tipo da ação (LOGIN, LOGOUT, etc.)
     * @param targetResource Recurso alvo da ação
     * @param severityLevel Nível de severidade (1-10)
     * @param bytesTransferred Bytes transferidos na ação
     */
    public Alerta(long timestamp, String userId, String sessionId, String actionType, 
                  String targetResource, int severityLevel, long bytesTransferred) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.sessionId = sessionId;
        this.actionType = actionType;
        this.targetResource = targetResource;
        this.severityLevel = severityLevel;
        this.bytesTransferred = bytesTransferred;
    }
    
    // Getters
    public long getTimestamp() {
        return timestamp;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public String getActionType() {
        return actionType;
    }
    
    public String getTargetResource() {
        return targetResource;
    }
    
    public int getSeverityLevel() {
        return severityLevel;
    }
    
    public long getBytesTransferred() {
        return bytesTransferred;
    }
    
    // Setters
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    
    public void setTargetResource(String targetResource) {
        this.targetResource = targetResource;
    }
    
    public void setSeverityLevel(int severityLevel) {
        this.severityLevel = severityLevel;
    }
    
    public void setBytesTransferred(long bytesTransferred) {
        this.bytesTransferred = bytesTransferred;
    }
    
    @Override
    public String toString() {
        return String.format("Alerta{timestamp=%d, userId='%s', sessionId='%s', actionType='%s', " +
                           "targetResource='%s', severityLevel=%d, bytesTransferred=%d}",
                           timestamp, userId, sessionId, actionType, targetResource, 
                           severityLevel, bytesTransferred);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Alerta alerta = (Alerta) obj;
        return timestamp == alerta.timestamp &&
               severityLevel == alerta.severityLevel &&
               bytesTransferred == alerta.bytesTransferred &&
               userId.equals(alerta.userId) &&
               sessionId.equals(alerta.sessionId) &&
               actionType.equals(alerta.actionType) &&
               targetResource.equals(alerta.targetResource);
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(timestamp) ^ userId.hashCode() ^ sessionId.hashCode() ^
               actionType.hashCode() ^ targetResource.hashCode() ^ 
               Integer.hashCode(severityLevel) ^ Long.hashCode(bytesTransferred);
    }
}
