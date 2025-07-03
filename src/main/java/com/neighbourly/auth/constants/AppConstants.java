package com.neighbourly.auth.constants;

public class AppConstants {

    public static String OTP_EMAIL_TEMPLATE="""
    Hi %s,

    Thanks for signing up for Neighbourly — the private network for gated communities.

    To verify your email and continue with registration, please use the OTP below:

    Your One-Time Password (OTP): %s

    This code is valid for the next 5 minutes. Please don’t share it with anyone.

    
    Neighbourly Team
    Simplifying community living.
    
    Need help? Reach us at hello@neighbourly.digital
    """;

    public static final String OTP_EMAIL_SUBJECT = "Your OTP for Neighbourly Registration";
}
