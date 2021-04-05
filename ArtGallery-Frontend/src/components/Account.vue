<template>
  <div id="account">
    <Logout/>
    <CustomerProfile v-if="$store.state.type === 'customer'" />
    <SellerProfile v-if="$store.state.type === 'seller'" />
    <ManagerProfile v-if="$store.state.type === 'manager'" />
    <div v-if="$store.state.type === ''">No Account Logged In</div>
  </div>
</template>

<script>
import CustomerProfile from "@/components/CustomerProfile";
import SellerProfile from "@/components/SellerProfile";
import ManagerProfile from "@/components/ManagerProfile";
import Logout from "@/components/Logout";

export default {
  name: "account",
  components: {
    CustomerProfile,
    SellerProfile,
    ManagerProfile,
    //Logout
  },
};
</script>
