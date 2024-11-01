package com.mobile.peticos.Perfil.Pet.Vacinas;



    public class ModelVacina {

        //{
        //  "idPet": 146,
        //  "name": "Rabies",
        //  "numDoses": 2   ,
        //  "dosesTaked":0
        //}

        private int idVaccine;
        private int idPet;
        private String name;
        private int numDoses;
        private int  dosesTaked;


        // Construtor

        public ModelVacina(int idVaccine,int idPet, String name, int numDoses, int dosesTaked) {
            this.idVaccine = idVaccine;
            this.idPet = idPet;
            this.name = name;
            this.numDoses = numDoses;
            this.dosesTaked = dosesTaked;
        }
//        public ModelVacina( int idVaccine, int idPet, String name, int numDoses) {
//            this.idVaccine = idVaccine;
//            this.idPet = idPet;
//            this.name = name;
//            this.numDoses = numDoses;
//        }
//        public ModelVacina( int idVaccine, int idPet, String dateDose, int numDoses) {
//            this.idVaccine = idVaccine;
//            this.idPet = idPet;
//            this.dateDose = dateDose;
//            this.numDoses = numDoses;
//        }

        public int getDosesTaked() {
            return dosesTaked;
        }

        public void setDosesTaked(int dosesTaked) {
            this.dosesTaked = dosesTaked;
        }

        // Getters e Setters
        public int getIdVaccine() {
            return this.idVaccine;
        }

        public void setIdVaccine(int idVaccine) {
            this.idVaccine = idVaccine;
        }

        public int getIdPet() {
            return idPet;
        }

        public void setIdPet(int idPet) {
            this.idPet = idPet;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumDoses() {
            return numDoses;
        }

        public void setNumDoses(int numDoses) {
            this.numDoses = numDoses;
        }


    }



