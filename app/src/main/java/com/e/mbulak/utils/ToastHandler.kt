package com.e.mbulak.utils

class ToastHandler {

    companion object {

        fun errorText (code : Int) : String {
            if(code == 409) {
                return "Номер уже зарегистрирован"
            } else if(code == 400) {
                return "Неверно набраны данные"
            } else if( code == 500) {
                return "Ошибка Сервера"
            } else if( code == 401) {
                return "Неавторизованный пользователь\n"
            } else if( code == 403) {
                return "Доступ запрещен или неверный токен\n"
            } else if( code == 404) {
                return "Метод или данные не найдены"
            } else if( code == 429) {
                return "Превышен лимит запросов\n"
            } else return "Error"


        }
    }

}