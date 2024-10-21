package com.mobile.peticos.Padrao.CallBack;

import com.mobile.peticos.Padrao.ModelRetorno;

public interface AuthCallback {
    void onSuccess(ModelRetorno perfil);

    void onError(String errorMessage);
}
