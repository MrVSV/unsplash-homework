package com.example.unsplashhomework.data.api

val KEY = "mj_2vu5NRd4iwh6pYzpqOymkmK79_WqclNutm5-O2UQ"
val SECRET_KEY = "C_vgTr-b2b6-ZdhllFr35MO5uNLk5mxGmFpuoq4-Bt0"
val authorization_uri = "urn:ietf:wg:oauth:2.0:oob"

/*
val call ="https://unsplash.com/oauth/authorize" +
        "?client_id=OOlbm6FJx2pFPfaU8uLWLbsQ1Ez0u1J-tXX5L8WLya8&" +
        "redirect_uri=urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob&" +
        "response_type=code" +
        "&scope=public+read_user+write_user+read_photos+write_photos+write_likes" +
        "+write_followers+read_collections+write_collections"*/

val call = "https://unsplash.com/oauth/authorize?client_id=mj_2vu5NRd4iwh6pYzpqOymkmK79_WqclNutm5-O2UQ&redirect_uri=urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob&response_type=code&scope=public+read_user+read_photos+write_likes+write_followers+read_collections"