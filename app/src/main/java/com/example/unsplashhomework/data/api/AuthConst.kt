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