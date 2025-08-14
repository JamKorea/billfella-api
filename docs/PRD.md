# BillFella – Product Requirements Document (PRD v1.1)

## 1. Product Overview
**BillFella** is a subscription and recurring payment management web service that helps users track all their **subscriptions**, **recurring bills**, and **fixed expenses** in one place.  
It sends payment reminders before due dates and provides insights into spending patterns.  
The service targets individual consumers and freelancers who want to simplify expense tracking.

## 2. Problem Statement
- Many users forget about their active subscriptions and payment dates, resulting in **unnecessary charges**.
- Payment reminders are scattered across different services, with **no unified view**.
- Existing budgeting apps are not optimized for **subscription-focused** management.

## 3. Goals & Objectives
**Primary Goals**
1. Allow users to manage all subscriptions/recurring payments in one dashboard.
2. Send email reminders before payment due dates.
3. Provide monthly/yearly expense analytics and visualizations.

**Secondary Goals**
- *(v2)* Integrate with third-party APIs for automatic subscription detection.
- *(v2)* Mobile app support.

## 4. Key Features (MVP Scope)
| Feature | Description | Priority |
|---------|-------------|----------|
| User Registration/Login | Email-password authentication with JWT | ★★★★★ |
| Subscription Entry | Add service name, amount, billing cycle, start date, notes | ★★★★★ |
| Subscription List | View all subscriptions for the logged-in user | ★★★★★ |
| Payment Reminder | Send email N days before payment date | ★★★★☆ |
| Dashboard Analytics | Monthly/yearly totals, category breakdown charts | ★★★☆☆ |
| Health Check API | Monitor server status (`/api/health`) | ★★★★★ |
| DB Check API | Verify DB connection and schema | ★★★★★ |

## 5. User Stories
1. **Busy professional** – I want reminders before due dates so I don’t get charged unnecessarily.  
2. **Freelancer** – I want to track all my recurring tool subscriptions in one place.  
3. **Budget-conscious user** – I want to analyze my subscription expenses by category to find cost-saving opportunities.

## 6. Competitive Analysis
| Service | Strengths | Weaknesses |
|---------|-----------|------------|
| Mint (US) | Automatic bank account integration | Limited/nonexistent Korean support |
| Money Manager | Great for general expense tracking | Weak in subscription-specific features |
| BillFella | Subscription-focused, reminders, planned automation | Manual input in v1 |

## 7. Success Metrics (KPI)
- **Retention rate**: ≥ 60% after 30 days  
- **Reminder email click-through rate**: ≥ 20%  
- **Average subscriptions per user**: ≥ 5  
- **Reduction in missed payments**: ≥ 50% (self-reported)

## 8. Constraints & Assumptions
**Constraints**
- v1: Web-based only (mobile via responsive design)
- Email reminders only (push notifications planned for v2)
- Subscription data entered manually in v1

**Assumptions**
- All users have a valid email address
- Billing cycles are weekly, monthly, or yearly

## 9. Roadmap
**Phase 1 (M1–M2)** – Backend setup, authentication, basic CRUD  
**Phase 2 (M3–M4)** – Reminder scheduler, email service  
**Phase 3 (M5)** – Analytics & dashboard, UI/UX improvements  
**Phase 4 (v2)** – Automatic subscription detection, mobile app

## 10. Risks & Mitigation
| Risk | Mitigation |
|------|------------|
| Low initial user adoption | Offer free trial or freemium plan |
| Missed reminders | Implement retry logic & error logging |
| Competitors enter the market | Differentiate with automation & advanced analytics |

## 11. Future Opportunities
- Integration with banking APIs (Open Banking)
- AI-based expense analysis & subscription recommendations
- Expansion into full budgeting and financial planning features
