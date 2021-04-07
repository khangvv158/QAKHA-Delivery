package com.sun.qakhadelivery.utils

import com.sun.qakhadelivery.data.model.*

fun products(): MutableList<Product> {
    return mutableListOf<Product>().apply {
        add(Product(1, "Barbecue Ribs", 92.08f, "Two butter croissants of your choice (plain, almond or cheese)", 1, Image()))
        add(Product(2, "Chicken Wings", 56.04f, "Three egg omelet with Roquefort cheese, chives, and ham. With a side of roasted potatoes, and your choice of toast or croissant.", 1,  Image()))
        add(Product(3, "Linguine with Clams", 55.51f, "Two butter croissants of your choice (plain, almond or cheese). With a side of herb butter or house-made hazelnut spread", 1,  Image()))
        add(Product(4, "Chicken Milanese", 75.55f, "Smoked salmon, poached eggs, diced red onions and Hollandaise sauce on an English muffin. With a side of roasted potatoes", 1,  Image()))
        add(Product(5, "Fish and Chips", 71.56f, "28-day aged 300g USDA Certified Prime Ribeye, rosemary-thyme garlic butter, with choice of two sides.", 1,  Image()))
    }
}

fun getPartnerMock(): MutableList<Partner> {
    return mutableListOf<Partner>().apply {
        add(Partner(0, "Quán 1", "63 Phạm Văn Nghị - Đà nẵng", Image(), 4.5f, "", "", "", "", "", null, null, mutableListOf()))
        add(Partner(1, "Quán 2", "01 Phạm Văn Nghị - Đà nẵng",  Image(), 4.7f, "", "", "", "", "", null, null, mutableListOf()))
        add(Partner(2, "Quán 3", "02 Phạm Văn Nghị - Đà nẵng",  Image(), 4.8f, "", "", "", "", "", null, null, mutableListOf()))
        add(Partner(3, "Quán 4", "03 Nguyễn Văn Linh - Đà nẵng",  Image(), 4.9f, "", "", "", "", "",null, null, mutableListOf()))
        add(Partner(4, "Quán 5", "54 Phạm Văn Nghị - Đà nẵng",  Image(), 4.2f, "", "", "", "", "", null, null, mutableListOf()))
        add(Partner(5, "Quán 6", "55 Phạm Văn Nghị - Đà nẵng",  Image(), 4.4f, "", "", "", "", "", null, null, mutableListOf()))
        add(Partner(6, "Quán 7", "99 Phạm Văn Nghị - Đà nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, mutableListOf()))
        add(Partner(7, "Quán 8", "12 Phạm Văn Nghị - Đà nẵng",  Image(), 4.2f, "", "", "", "", "", null, null, mutableListOf()))
    }
}

fun getPartnerByIdType(idType: Int, result: (MutableList<Partner>) -> Unit) {
    if (idType == 0) {
        val ds =mutableListOf<Partner>().apply {
            add(Partner(0, "Quán 0 - 1", "63 Phạm Văn Nghị - Đà nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, mutableListOf()))
            add(Partner(1, "Quán 0 - 2", "01 Phạm Văn Nghị - Đà nẵng",  Image(), 4.7f, "", "", "", "", "",null, null, mutableListOf()))
            add(Partner(2, "Quán 0 - 3", "02 Phạm Văn Nghị - Đà nẵng",  Image(), 4.8f, "", "", "", "", "", null, null, mutableListOf()))
        }
        result(ds)
    }
    if (idType == 1) {
        val ds = mutableListOf<Partner>().apply {
            add(Partner(0, "Quán 1 - 1", "63 Phạm Văn Nghị - Đà nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, mutableListOf()))
            add(Partner(1, "Quán 1 - 2", "01 Phạm Văn Nghị - Đà nẵng",  Image(), 4.7f, "", "", "", "", "", null, null, mutableListOf()))
            add(Partner(2, "Quán 1 - 3", "02 Phạm Văn Nghị - Đà nẵng",  Image(), 4.8f, "", "", "", "", "", null, null, mutableListOf()))
        }
        result(ds)
    }
    if (idType == 2) {
        val ds = mutableListOf<Partner>().apply {
            add(Partner(0, "Quán 2 - 1", "63 Phạm Văn Nghị - Đà nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, mutableListOf()))
            add(Partner(1, "Quán 2 - 2", "01 Phạm Văn Nghị - Đà nẵng",  Image(), 4.7f, "", "", "", "", "",null, null, mutableListOf()))
            add(Partner(2, "Quán 2 - 3", "02 Phạm Văn Nghị - Đà nẵng",  Image(), 4.8f, "", "", "", "", "",null, null, mutableListOf()))
        }
        result(ds)
    }
    if (idType == 3) {
        val ds = mutableListOf<Partner>().apply {
            add(Partner(0, "Quán 3 - 1", "63 Phạm Văn Nghị - Đà nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, mutableListOf()))
            add(Partner(1, "Quán 3 - 2", "01 Phạm Văn Nghị - Đà nẵng",  Image(), 4.7f, "", "", "", "", "",null, null, mutableListOf()))
            add(Partner(2, "Quán 3 - 3", "02 Phạm Văn Nghị - Đà nẵng",  Image(), 4.8f, "", "", "", "", "", null, null, mutableListOf()))
        }
        result(ds)
    }
}

fun getOrderHistory(): MutableList<Order> {
    return mutableListOf<Order>().apply {
        add(Order(0, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(1, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(2, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(3, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(4, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(5, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(6, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
    }
}

fun getOrderShipping(): MutableList<Order> {
    return mutableListOf<Order>().apply {
        add(Order(0, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(1, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(2, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(3, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(4, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(5, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
        add(Order(6, "", "4 - 4 - 2021", Partner(0, "Kichi Coffer & Drink", "K63 Phạm văn nghị , Đà Nẵng",  Image(), 4.5f, "", "", "", "", "", null, null, arrayListOf()), 0, 10.000f))
    }
}

fun getFirstPartner():Partner{
    return  Partner(0, "Quán 1", "63 Phạm Văn Nghị - Đà nẵng",  Image(), 4.5f, "0353167485", "", "", "", "",null ,null , getCategory())
}

fun getCategory(): MutableList<Category> {
    return mutableListOf<Category>().apply{
        add(Category(1,"Japanese",1,1,products()))
        add(Category(4,"Vietnam",1,1,products()))
    }
}
