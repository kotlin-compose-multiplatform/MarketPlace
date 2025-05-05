- [X] Translate uzbek
- [X] Store single product app bar
- [X] Home Countries and regions
- [X] Favorites
- [X] Phone number
- [X] Products basket button
- [X] Search page
- [X] Check basket online payment
- [X] Basket page UI
- [X] Auth UI
- [X] Add product multiple currency
- [X] Add product select images
- [X] Check locations of store
- [ ] Check search
- [X] Check basket
- [X] All add basket buttons
  65829174
- 65829173
- 1111


guestid:            f00c6ce2099d9605f96c9f191ce2e55a
guestid:            f00c6ce2099d9605f96c9f191ce2e55a
guestid:            10ed461ad30afda4dae6329de2cec8cf


guestid:            af8d98781cd51272260f1de287cb3d5a
guestid:            af8d98781cd51272260f1de287cb3d5a



Shahzad Halliev <hallievshahzad7@gmail.com>
сб, 8 февр., 22:34 (2 дня назад)
кому: мне

> Shahzod: 1)magazin adyny telefon tiktok instagram calsylandan son calsylvar backendte yone telefonda refresh etmeli
2)dukanyn harytlary spisgogy cykando 3 yurdyn bahasyny gorkezmeli
24 TMT
2 KZT
3 USM

https://komekchi.com.tm/api/v1/admin/products/1076
https://komekchi.com.tm/api/v1/admin/products

prices:[{price: 20, discount: 0, discountPrice: "0.00", currency: "UZS"},
{price: 0, discount: 0, discountPrice: "0.00", currency: "TMT"},
{price: 0, discount: 0, discountPrice: "0.00", currency: "KZT"}]

3)product update image gosup bolanok
4)product update boleginde kategoriya saylanan yagdayda bolmaly
5)dukan doreden son dukanym bolegine derek dukanym doretmek cykya telefondan girip cykylsa dukanym bolegi cykyar.
6)dukanym icindaki harytlarym icine kawagt girilse telefondan cykya(programmadan cykya) >
Shahzod: dukanlar boleginde dukan tester diyip aslynda bir haryt bar yone 1harydy menzes edip 2 sanysyny cykaryar. > 
Shahzod: Salamalekum agam gowmy yagdaylarynyz su issue duzedip berayinde > Shahzod: magazin icindaki toleg ayrmaly
user toleg goymaly

6 oshibkada iphone-da haryt saldym prices almandyr sona prices bosh barya shonamy ya programmadan cykaryp bilermi sebabi bashga dukanlarda harytlaram girya.


> Shahzod:bez registratsiyasys zakaz sheyle body ugradylyar.

{
"orders": [
{
"productId": 4,
"count": 2
}
],
"guest":{
"name":"guest name",
"address":"dom1",
"phoneNumber":"865829174"
}
"paymentMethod": "cash",
}

> Shahzod:
header icine bolsa
"guestId":"er45323-232-234wq"

> Shahzod:
onki url
komekchi.com.tm/api/v1/client/orders

> Shahzod:
GET POST - bez registratsiyasys zakaz edende guestId registratsiya bolsa authorization diyip header icine ugratmaly

> Shahzod:
guestId
shul api -dan alyp bilyan
komekchi.com.tm/api/v1/client/guest
> 
> 
> 
> 
> 
> ##########



store-token: 
  
Api uytgeshemeler bo
Shahzod, [12/16/24 5:30 PM]
http://216.250.9.97:3000/swagger/#/admin-store/post_admin_products
price kop yurtly goshuldy


Shahzod, [12/16/24 5:34 PM]
http://216.250.9.97:3000/swagger/#/admin-store/post_admin_payment_pay_store_with_key
shul api dukan toleg gecmek promo code

Shahzod, [12/16/24 5:34 PM]
http://216.250.9.97:3000/swagger/#/admin-store/post_admin_payment_pay_store_with_bank
bank toleg etmek ucin

Shahzod, [12/16/24 5:35 PM]
http://216.250.9.97:3000/swagger/#/product/get_client_products
filter yurt region basgalar


Shahzod, [12/16/24 5:36 PM]
bashga galan zatlar uytgemedi

Shahzod, [12/16/24 5:38 PM]
sul apilar ustunde ishlenilyar
1)chat
2)store toleg list
3)products search

################# DONE ###############
Shahzod, [12/16/24 5:31 PM]
http://216.250.9.97:3000/swagger/#/category/get_client_categories
totalProducts gelya indi



##############

Magazin -> Haryt -> product -> Update
Update knopka basylsa telefondan cykaryar

java.lang.VerifyError: Verifier rejected class d6.u0: void d6.u0.a(com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel, k1.p0, androidx.compose.runtime.Composer, int, int) failed to verify: void d6.u0.a(com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel, k1.p0, androidx.compose.runtime.Composer, int, int): [0xFF0] register v330 has type Undefined but expected Integer (declaration of 'd6.u0' appears in /data/app/~~cN5foa93vfXNc4J0A6Jang==/com.komekci.marketplace-bZVQX0C3ENLF9nExc9ro_Q==/base.apk!classes2.dex)
at d6.u0.a(Unknown Source:0)
at b6.u1.a(SourceFile:325)
at b6.u1.invoke(SourceFile:100)

Magazin -> Sargytlarym -> zakaz -> haryt
Zakaz haryt bosh gelya

java.lang.IndexOutOfBoundsException: Index: 1, Size: 1


Dukan card toleg etmekde telefon programmadan cykya

java.lang.IllegalStateException: TypeToken must be created with a type argument: new TypeToken<...>() {}; When using code shrinkers (ProGuard, R8, ...) make sure that generic signatures are preserved.
2025-01-13 22:53:39.201  8362-8362  System.err              com.komekci.marketplace              W  	at i4.a.<init>(SourceFile:3)



Shahzod, [24 Jan 2025 at 21:33:07]:
https://komekchi.com.tm/swagger/#/admin-store/delete_admin_products_upload_image__productId_

For delete image


Shahzod:
https://komekchi.com.tm/swagger/#/admin-store/delete_admin_products_upload_image__productId_

For delete image

https://komekchi.com.tm/swagger/#/admin-store/get_admin_products__productId_

Fixed prices get with id

https://komekchi.com.tm/swagger/#/admin-store/put_admin_products__productId_

You can update prices

https://komekchi.com.tm/swagger/#/user/put_client_user

You can update region country

Store location update etjek bolsan url
https://komekchi.com.tm/api/v1/admin/store/location/1
{
  countryId=3
  regionId = 6
}