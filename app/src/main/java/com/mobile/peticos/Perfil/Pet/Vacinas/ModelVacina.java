package com.mobile.peticos.Perfil.Pet.Vacinas;



    public class ModelVacina {

        private int idVaccine;
        private int idPet;
        private String name;
        private int numDoses;
        private int  dateDose;


        // Construtor
        public ModelVacina( int idPet, String name, int numDoses) {
            this.idPet = idPet;
            this.name = name;
            this.numDoses = numDoses;
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

        // Getters e Setters
        public int getIdVaccine() {
            return idVaccine;
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



