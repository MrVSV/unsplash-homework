package com.example.unsplashhomework.data.api

const val ACCESS_KEY = "TQBtxa7iGDE6aUYk12mA1mWdM7qLwJrkKgIsNXYEbtY"
const val SECRET_KEY = "rA8Ntvr_vPSHofWpYz4Ib4coselWLvv8hcwjTv8lOA0"
const val REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob"
const val SCOPE =
    "public+read_user+" +
            "write_user+read_photos+" +
            "write_photos+" +
            "write_likes+" +
            "write_followers+" +
            "read_collections+" +
            "write_collections"

const val call =
    "https://unsplash.com/oauth/authorize" +
            "?client_id=" + ACCESS_KEY +
            "&redirect_uri=" + REDIRECT_URI +
            "&response_type=code" +
            "&scope=" + SCOPE


const val TOKEN_SHARED_NAME ="Pref_Token"

//раз
//const val ACCESS_KEY = "OOlbm6FJx2pFPfaU8uLWLbsQ1Ez0u1J-tXX5L8WLya8"
//const val SECRET_KEY = "70SAVhXIiZ6ySujad4zR7b4-PZ9TymGOU_H_qDu-9_A"

//два
//const val ACCESS_KEY = "TQBtxa7iGDE6aUYk12mA1mWdM7qLwJrkKgIsNXYEbtY"
//const val SECRET_KEY = "rA8Ntvr_vPSHofWpYz4Ib4coselWLvv8hcwjTv8lOA0"