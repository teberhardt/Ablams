<template>
    <v-app id="inspire">
        <v-content>
            <v-container fluid fill-height>
                <v-layout align-center justify-center>
                    <v-flex xs12 sm8 md4>
                        <v-card class="elevation-12">
                            <v-toolbar dark color="primary">
                                <v-toolbar-title>Login form</v-toolbar-title>
                            </v-toolbar>
                            <v-card-text>
                                <v-form>
                                    <v-text-field
                                        prepend-icon="mdi-account"
                                        name="login"
                                        label="Login"
                                        type="text"
                                        v-model="userName"
                                    ></v-text-field>
                                    <v-text-field
                                        id="password"
                                        prepend-icon="mdi-lock"
                                        name="password"
                                        label="Password"
                                        type="password"
                                        v-model="password"
                                    ></v-text-field>
                                </v-form>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="primary" v-on:click="login()">Login</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
import {Component, Vue} from "vue-property-decorator";
import AuthService from "@/auth/auth.service";
import AuthUser from "@/auth/AuthUser";

@Component
export default class Login extends Vue {

    userName = ''
    password = ''

    login(){
        let user = new AuthUser(this.userName, this.password);
        AuthService.login(user).then(() =>
            {

            }
        )
    }

}
</script>

<style></style>
