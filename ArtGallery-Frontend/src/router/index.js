import Vue from 'vue'
import Router from 'vue-router'
import ArtGallery from '@/components/ArtGallery'
import Login from '@/components/Login'
import Signup from '@/components/Signup'
import Account from '@/components/Account'
import AllProducts from '@/components/AllProducts'
import AllSellers from '@/components/AllSellers'
import AllPromotions from '@/components/AllPromotions'
import CustomerProfile from '@/components/CustomerProfile'
import ManagerProfile from '@/components/ManagerProfile'
import SellerDisplay from '@/components/SellerDisplay'
import SellerProfile from '@/components/SellerProfile'
import ProductCreation from '@/components/ProductCreation'
import Withdrawl from '@/components/Withdrawl'
import SellerInfo from '@/components/SellerInfo'
import SellerProductInfo from '@/components/SellerProductInfo'
import MakeOrder from '@/components/MakeOrder'
import SearchProduct from '@/components/SearchProduct'
import CustomerInfo from '@/components/CustomerInfo'
import MakePromotion from '@/components/MakePromotion'
import customerReception from '@/components/customerReception'


Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'ArtGallery',
      component: ArtGallery
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/signup',
      name: 'Signup',
      component: Signup
    },
    {
      path: '/AllProducts',
      name: 'Products',
      component: AllProducts
    },
    {

      path: '/AllSellers',
      name: 'AllSellers',
      component: AllSellers
    },
    {
      path: '/AllPromotions',
      name: 'AllPromotions',
      component: AllPromotions
    },
    {
      path: '/CustomerProfile',
      name: 'CustomerProfile',
      component: CustomerProfile
    },
    // test purpose for manager profile
    {
      path: '/ManagerProfile',
      name: 'ManagerProfile',
      component: ManagerProfile
    },
    {
      path: '/Seller',
      name: 'Seller',
      component: SellerDisplay
    },
    {
      path: '/SellerProfile',
      name: 'SellerProfile',
      component: SellerProfile
    },
    {
      path: '/createproduct',
      name: 'createproduct',
      component: ProductCreation
    },
    {
      path: '/withdrawl',
      name: 'withdrawl',
      component: Withdrawl
    },
    {
      path: '/sellerinfo',
      name: 'sellerinfo',
      component: SellerInfo
    },
    {
      path: '/sellerproductinfo',
      name: 'SellerProductInfo',
      component: SellerProductInfo
    },
    {
      path: '/makeorder',
      name: 'makeorder',
      component: MakeOrder
    },
    {
      path: '/customerinfo',
      name: 'cutstomerinfo',
      component: CustomerInfo
    },
    {
      path: '/product',
      name: 'product',
      component: SearchProduct
    },
    {
      path: '/account',
      name: 'Account',
      component: Account
    },
    {
      path: '/MakePromotion',
      name: 'makePromotion',
      component: MakePromotion
    },
    {
      path: '/customerreception',
      name: 'customerreception',
      component: customerReception
    }
  ]
})
