package com.e.mbulak

import android.app.Application
import com.e.mbulak.data.Repository
import com.e.mbulak.data.remote.Api

class App : Application() {

    companion object {
        var repository : Repository = Repository(Api.getInstance())
    }

}