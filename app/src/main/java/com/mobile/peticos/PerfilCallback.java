package com.mobile.peticos;

import com.mobile.peticos.Cadastros.APIs.ModelPerfil;

public interface PerfilCallback {
    void onSuccess(ModelPerfil perfil);

    void onError(String errorMessage);
}
