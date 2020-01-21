package com.infoicon.bonjob.seeker.profile;

import java.util.List;

/**
 * Created by info on 22/8/18.
 */

public class GetJobSoughtFragmentResponce {


    /**
     * success : true
     * error : false
     * candidateSeeks : [{"candidate_seek_content_id":"2","candidate_seek_id":"1","candidate_seek_title":"Apprentissage","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"4","candidate_seek_id":"2","candidate_seek_title":"Stage","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"6","candidate_seek_id":"3","candidate_seek_title":"Adjoint(e) de direction","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"8","candidate_seek_id":"4","candidate_seek_title":"Agent de réservation","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"10","candidate_seek_id":"5","candidate_seek_title":"Attaché(e) commercial(e)","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"12","candidate_seek_id":"6","candidate_seek_title":"Bagagiste","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"14","candidate_seek_id":"7","candidate_seek_title":"Barman","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"16","candidate_seek_id":"8","candidate_seek_title":"Boucher","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"18","candidate_seek_id":"9","candidate_seek_title":"Boulanger","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"20","candidate_seek_id":"10","candidate_seek_title":"Charcutier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"22","candidate_seek_id":"11","candidate_seek_title":"Chargé(e) de projets événementiels","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"24","candidate_seek_id":"12","candidate_seek_title":"Chasseur","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"26","candidate_seek_id":"13","candidate_seek_title":"Chef cuisinier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"28","candidate_seek_id":"14","candidate_seek_title":"Chef cuisinier en restauration collective","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"30","candidate_seek_id":"15","candidate_seek_title":"Chef d'équipe en restauration collective","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"32","candidate_seek_id":"16","candidate_seek_title":"Chef d'équipe en restauration rapide","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"34","candidate_seek_id":"17","candidate_seek_title":"Chef de cuisine","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"36","candidate_seek_id":"18","candidate_seek_title":"Chef de maintenance","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"38","candidate_seek_id":"19","candidate_seek_title":"Chef de partie","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"40","candidate_seek_id":"20","candidate_seek_title":"Chef de production en restauration collective","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"42","candidate_seek_id":"21","candidate_seek_title":"Chef de rang","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"44","candidate_seek_id":"22","candidate_seek_title":"Chef de rang room service","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"46","candidate_seek_id":"23","candidate_seek_title":"Chef de réception","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"48","candidate_seek_id":"24","candidate_seek_title":"Chef gérant","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"50","candidate_seek_id":"25","candidate_seek_title":"Chef sommelier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"52","candidate_seek_id":"26","candidate_seek_title":"Chocolatier / Confiseur","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"54","candidate_seek_id":"27","candidate_seek_title":"Commis de cuisine","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"56","candidate_seek_id":"28","candidate_seek_title":"Commis de salle","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"58","candidate_seek_id":"29","candidate_seek_title":"Concierge d'hôtel","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"60","candidate_seek_id":"30","candidate_seek_title":"Concierge de grand hôtel","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"62","candidate_seek_id":"31","candidate_seek_title":"Crêpier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"64","candidate_seek_id":"32","candidate_seek_title":"Cuisinier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"66","candidate_seek_id":"33","candidate_seek_title":"Cuisinier dans la fonction publique","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"68","candidate_seek_id":"34","candidate_seek_title":"Diététicien(ne)","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"70","candidate_seek_id":"35","candidate_seek_title":"Diététicien(ne) d'exploitation","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"72","candidate_seek_id":"36","candidate_seek_title":"Directeur adjoint en restauration rapide","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"74","candidate_seek_id":"37","candidate_seek_title":"Directeur commercial","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"76","candidate_seek_id":"38","candidate_seek_title":"Directeur d\u2019hôtel","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"78","candidate_seek_id":"39","candidate_seek_title":"Directeur de l'hébergement","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"80","candidate_seek_id":"40","candidate_seek_title":"Directeur de la restauration","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"82","candidate_seek_id":"41","candidate_seek_title":"Directeur de restaurant","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"84","candidate_seek_id":"42","candidate_seek_title":"Directeur en restauration rapide","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"86","candidate_seek_id":"43","candidate_seek_title":"Ecailler","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"88","candidate_seek_id":"44","candidate_seek_title":"Économe","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"90","candidate_seek_id":"45","candidate_seek_title":"Employé(e) d\u2019étage","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"92","candidate_seek_id":"46","candidate_seek_title":"Employé(e) de café","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"94","candidate_seek_id":"47","candidate_seek_title":"Employé(e) de restauration","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"96","candidate_seek_id":"48","candidate_seek_title":"Employé(e) de restauration secteur Cuisine","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"98","candidate_seek_id":"49","candidate_seek_title":"Employé(e) de restauration secteur Service","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"100","candidate_seek_id":"50","candidate_seek_title":"Employé(e) polyvalent(e) en restauration collective","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"102","candidate_seek_id":"51","candidate_seek_title":"Équipier en restauration rapide","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"104","candidate_seek_id":"52","candidate_seek_title":"Exploitant de café, bar, brasserie","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"106","candidate_seek_id":"53","candidate_seek_title":"Exploitant en restauration","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"108","candidate_seek_id":"54","candidate_seek_title":"Femme de chambre","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"110","candidate_seek_id":"55","candidate_seek_title":"Fromager","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"112","candidate_seek_id":"56","candidate_seek_title":"Garçon de café","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"114","candidate_seek_id":"57","candidate_seek_title":"Gérant / Chef-gérant en restauration collective","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"116","candidate_seek_id":"58","candidate_seek_title":"Gouvernante","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"118","candidate_seek_id":"59","candidate_seek_title":"Gouvernante générale","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"120","candidate_seek_id":"60","candidate_seek_title":"Groom","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"122","candidate_seek_id":"61","candidate_seek_title":"Guest relation manager","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"124","candidate_seek_id":"62","candidate_seek_title":"Hôte / Hôtesse d'accueil","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"126","candidate_seek_id":"63","candidate_seek_title":"Liftier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"128","candidate_seek_id":"64","candidate_seek_title":"Limonadier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"130","candidate_seek_id":"65","candidate_seek_title":"Lingère","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"132","candidate_seek_id":"66","candidate_seek_title":"Maître d\u2019hôtel","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"134","candidate_seek_id":"67","candidate_seek_title":"Majordome","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"136","candidate_seek_id":"68","candidate_seek_title":"Manager dans la restauration","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"138","candidate_seek_id":"69","candidate_seek_title":"Manager en restauration rapide","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"140","candidate_seek_id":"70","candidate_seek_title":"Manager spécialisé dans le luxe","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"142","candidate_seek_id":"71","candidate_seek_title":"Night Auditor","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"144","candidate_seek_id":"72","candidate_seek_title":"Pâtissier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"146","candidate_seek_id":"73","candidate_seek_title":"Pizzaïolo","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"148","candidate_seek_id":"74","candidate_seek_title":"Plongeur","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"150","candidate_seek_id":"75","candidate_seek_title":"Poissonnier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"152","candidate_seek_id":"76","candidate_seek_title":"Portier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"154","candidate_seek_id":"77","candidate_seek_title":"Préparateur / Vendeur en restauration rapide","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"156","candidate_seek_id":"78","candidate_seek_title":"Réceptionniste","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"158","candidate_seek_id":"79","candidate_seek_title":"Réceptionniste de nuit","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"160","candidate_seek_id":"80","candidate_seek_title":"Responsable de salle","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"162","candidate_seek_id":"81","candidate_seek_title":"Responsable room service","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"164","candidate_seek_id":"82","candidate_seek_title":"Revenue ou Yield Manager","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"166","candidate_seek_id":"83","candidate_seek_title":"Second de cuisine","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"168","candidate_seek_id":"84","candidate_seek_title":"Serveur","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"170","candidate_seek_id":"85","candidate_seek_title":"Sommelier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"172","candidate_seek_id":"86","candidate_seek_title":"Sous-chef","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"174","candidate_seek_id":"87","candidate_seek_title":"Spa manager","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"176","candidate_seek_id":"88","candidate_seek_title":"Technicien de maintenance","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"178","candidate_seek_id":"89","candidate_seek_title":"Traiteur","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"180","candidate_seek_id":"90","candidate_seek_title":"Traiteur / Organisateur de réception","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"182","candidate_seek_id":"91","candidate_seek_title":"Valet de chambre","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"184","candidate_seek_id":"92","candidate_seek_title":"Veilleur de nuit","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"186","candidate_seek_id":"93","candidate_seek_title":"Vendeur","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"188","candidate_seek_id":"94","candidate_seek_title":"Voiturier","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 10:59:28"},{"candidate_seek_content_id":"190","candidate_seek_id":"95","candidate_seek_title":"Autre","language_id":"2","status":"1","addedBy":"1","updatedOn":"2018-07-17 11:03:10"}]
     * msg : Données trouvées
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private String msg;
    private String active_user;
    private List<CandidateSeeksBean> candidateSeeks;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    public List<CandidateSeeksBean> getCandidateSeeks() {
        return candidateSeeks;
    }

    public void setCandidateSeeks(List<CandidateSeeksBean> candidateSeeks) {
        this.candidateSeeks = candidateSeeks;
    }

    public static class CandidateSeeksBean {
        /**
         * candidate_seek_content_id : 2
         * candidate_seek_id : 1
         * candidate_seek_title : Apprentissage
         * language_id : 2
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-17 10:59:28
         */

        private String candidate_seek_content_id;
        private String candidate_seek_id;
        private String candidate_seek_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getCandidate_seek_content_id() {
            return candidate_seek_content_id;
        }

        public void setCandidate_seek_content_id(String candidate_seek_content_id) {
            this.candidate_seek_content_id = candidate_seek_content_id;
        }

        public String getCandidate_seek_id() {
            return candidate_seek_id;
        }

        public void setCandidate_seek_id(String candidate_seek_id) {
            this.candidate_seek_id = candidate_seek_id;
        }

        public String getCandidate_seek_title() {
            return candidate_seek_title;
        }

        public void setCandidate_seek_title(String candidate_seek_title) {
            this.candidate_seek_title = candidate_seek_title;
        }

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddedBy() {
            return addedBy;
        }

        public void setAddedBy(String addedBy) {
            this.addedBy = addedBy;
        }

        public String getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            this.updatedOn = updatedOn;
        }
    }
}
