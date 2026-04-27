# 🔥 Gas Delivery — Server App

> **Worker / Business** Android application of the **Gas Delivery Vlora** system. Workers see incoming customer orders, view details, track the customer's location on Google Maps, and complete deliveries.

[![Platform](https://img.shields.io/badge/platform-Android-3DDC84)]()
[![Language](https://img.shields.io/badge/language-Java-orange)]()
[![Firebase](https://img.shields.io/badge/backend-Firebase-yellow)]()
[![SQLite](https://img.shields.io/badge/local-SQLite-blue)]()
[![Course](https://img.shields.io/badge/course-HCI-purple)]()

---

## 📌 Overview

This is the **worker-side companion** to the [GasDeliveryClient](https://github.com/denisvreshtazi/GasDeliveryClient) app. Together they form a small two-app delivery system designed during a Human-Computer Interaction course, using **Vlorë (Vlora), Albania** as the case study:

- **Customers** order gas through `GasDeliveryClient`
- **Workers** receive and fulfill orders through `GasDeliveryServer` (this repo)

The app handles authentication, real-time order updates from Firebase, navigation to the customer's location, and direct phone calls.

![logo](logo.png)

## 🛠️ Tech Stack

- **Android Studio** (Java)
- **Firebase Realtime Database** — order sync between client and worker apps
- **SQLite** — local cache
- **Google Maps Android SDK** — order tracking and navigation
- **Material Design** components
- **Drawer.io** — wireframes & UI mock-ups

## 🧱 Architecture

```
┌────────────────────────────────────────────────┐
│             GasDeliveryServer (Worker)         │
│                                                │
│  MainActivity (Login)                          │
│       │                                        │
│       ▼                                        │
│  Home (Order list, RecyclerView)               │
│       │                                        │
│       ├─▶ OrderDetails (CardView + items)      │
│       │                                        │
│       └─▶ TrackingOrder (Google Maps)          │
│                ├─▶ Phone Call                  │
│                └─▶ Open in Google Maps         │
└────────────────────────────────────────────────┘
                    ▲
                    │ Firebase Realtime DB
                    ▼
┌────────────────────────────────────────────────┐
│             GasDeliveryClient (Customer)       │
└────────────────────────────────────────────────┘
```

## 🗂️ Project Structure

### Activities
Located at `/app/src/main/java/com/example/gasdeliveryserver/`

| Activity | Layout | Description |
|---|---|---|
| `MainActivity.java` | `layout/activity_main.xml` | Worker login. Validates against the database; on success navigates to `Home`. |
| `Home.java` | `layout/activity_home.xml` + `content_home.xml` | Lists incoming orders (loaded from Firebase). Each order rendered via `OrderViewHolder` (`layout/order_layout.xml`). Requests location and phone-call permissions. |
| `OrderDetails.java` | `layout/activity_order_details.xml` | Header `CardView` with order metadata + a `RecyclerView` of products via `OrderDetailsAdapter` (`layout/order_details_layout.xml`). |
| `TrackingOrder.java` | `layout/activity_tracking_order.xml` | Google Maps activity with two markers (**buyer**, **worker**). Buttons open the dialer or Google Maps for turn-by-turn navigation. |

### Models (`com.example.gasdeliveryserver.model`)
- **`Order`** — order properties
- **`User`** — user properties
- **`Request`** — request properties

### ViewHolders / Adapters
- **`MyViewHolder`** — single product card (name, price, checkbox)
- **`OrderDetailsAdapter`** — bound to `layout/order_details_layout.xml`
- **`OrderViewHolder`** — order summary card

### Common
- **`Common.java`** — holds the currently logged-in worker so all subsequent actions are attributed correctly.

### Resources

| Path | Contents |
|---|---|
| `app/src/main/res/layout/` | All activity & item XML layouts |
| `app/src/main/res/layout/menu/` | Navigation drawer layouts |
| `app/src/main/res/drawable/` | Icons & images |

### Documents
- `Needefinding.pdf` — needfinding study and personas
- `client test.pdf` — client-side usability tests
- `worker test.pdf` — worker-side usability tests
- `HCI_Vreshtazi.pdf` — full coursework report
- `HCI_Vreshtazi_Presentation.pdf` — final presentation slides

## 🚀 Setup & Run

### Prerequisites
- **Android Studio** (Arctic Fox or newer recommended)
- **Android SDK** with API level matching the project's `compileSdkVersion`
- A **Firebase project** with the Realtime Database enabled
- A **Google Maps API key** (for `TrackingOrder`)

### Steps

1. Clone the repo:
   ```bash
   git clone https://github.com/denisvreshtazi/GasDeliveryServer.git
   ```
2. Open the project in **Android Studio** and let Gradle sync.
3. Add your **`google-services.json`** under `app/` (download from your Firebase console).
4. In `AndroidManifest.xml` (or `local.properties`), set your **Google Maps API key**.
5. Connect a device or start an emulator (with location services enabled).
6. **Run** ▶️.

## 🔐 Permissions Used

- `INTERNET` — Firebase + Google Maps
- `ACCESS_FINE_LOCATION` — show worker position on map
- `CALL_PHONE` — direct dial of the customer

## 🧪 User Testing

The repository ships with the **needfinding** report and **usability test** notes from the course (`Needefinding.pdf`, `worker test.pdf`, `client test.pdf`) — useful background on the design rationale.

## 🔗 Related Project

➡️ **Customer-side app:** [GasDeliveryClient](https://github.com/denisvreshtazi/GasDeliveryClient)

## 👤 Author

**Denis Vreshtazi** — [GitHub](https://github.com/denisvreshtazi)
