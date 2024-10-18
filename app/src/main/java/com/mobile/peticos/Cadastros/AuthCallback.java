package com.mobile.peticos.Cadastros;

import com.mobile.peticos.Cadastros.APIs.ModelPerfilAuth;
import com.mobile.peticos.ModelRetorno;

public interface AuthCallback {
    void onSuccess(ModelRetorno perfil);

    void onError(String errorMessage);
}
