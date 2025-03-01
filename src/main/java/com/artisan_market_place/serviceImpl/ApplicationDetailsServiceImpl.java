package com.artisan_market_place.serviceImpl;

import com.artisan_market_place.service.ApplicationDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ApplicationDetailsServiceImpl implements ApplicationDetailsService {
    @Override
    public String getApplicationDetails() {
        return "Application Overview:\n" +
                "This web application is an end-to-end e-commerce platform that connects sellers, buyers, and delivery personnel. " +
                "Sellers can list and manage their products, buyers can browse, purchase, and track orders, and delivery personnel " +
                "facilitate seamless product delivery. The platform ensures a streamlined shopping experience with secure payments, " +
                "real-time order tracking, and user-friendly interfaces for all stakeholders.\n\n" +

                "Key Features:\n" +
                "- Sellers can register, list products, manage inventory, and track sales.\n" +
                "- Buyers can explore a wide range of products, make secure purchases, and track their orders.\n" +
                "- Delivery personnel handle timely deliveries with optimized routing and tracking features.\n" +
                "- Integrated payment gateways ensure secure transactions.\n" +
                "- A robust review and rating system enhances transparency and trust.\n\n" +

                "Terms and Conditions:\n" +
                "1. **User Registration & Eligibility**\n" +
                "   - All users (sellers, buyers, and delivery personnel) must register with valid credentials.\n" +
                "   - Sellers must provide accurate product details and adhere to legal guidelines.\n" +
                "   - Buyers must provide valid payment information and delivery addresses.\n\n" +

                "2. **Platform Usage**\n" +
                "   - Users must not engage in fraudulent transactions, false advertising, or misuse of services.\n" +
                "   - The platform reserves the right to suspend accounts that violate terms.\n" +
                "   - All interactions between buyers, sellers, and delivery personnel must comply with ethical and legal standards.\n\n" +

                "3. **Payments & Refunds**\n" +
                "   - Payments are processed securely through third-party gateways.\n" +
                "   - Refunds and cancellations are subject to individual seller policies.\n" +
                "   - Buyers must initiate return requests within the designated period.\n\n" +

                "4. **Delivery & Logistics**\n" +
                "   - Delivery personnel must ensure timely and secure delivery of products.\n" +
                "   - Buyers must be available at the provided delivery address during the estimated delivery window.\n\n" +

                "5. **Liability & Disclaimers**\n" +
                "   - The platform acts as a marketplace and is not liable for issues arising between buyers and sellers.\n" +
                "   - Any disputes must be resolved through the platform’s designated dispute resolution mechanism.\n" +
                "   - The platform is not responsible for damages or losses incurred due to third-party service failures.\n\n" +

                "6. **Privacy & Data Protection**\n" +
                "   - User data is collected and processed in accordance with privacy laws.\n" +
                "   - Personal information will not be shared with third parties without user consent.\n\n" +

                "7. **Changes to Terms**\n" +
                "   - The platform reserves the right to update these terms as necessary.\n" +
                "   - Users will be notified of significant changes, and continued use of the platform implies acceptance.\n\n" +

                "By using this platform, all users agree to abide by these Terms and Conditions. Failure to comply may result in " +
                "account suspension or termination.";
    }

}
