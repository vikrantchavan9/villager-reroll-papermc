VillagerReroll 🧙‍♂️🔁

A Paper / Spigot Minecraft plugin that allows rerolling villager trades directly from the vanilla trade UI, without breaking job site blocks.

This plugin preserves true vanilla trade logic while providing a smooth, intuitive reroll experience.

✨ Features

🔁 Reroll villager trades from inside the vanilla trade screen

🧠 Uses true vanilla trade generation (no hardcoded trades)

🧱 Job site blocks remain untouched

⭐ Reroll appears as a fake villager trade (feels native)

🛡️ Safe & future-proof (no NMS, no packet hacks)

🚀 Compatible with Paper 1.20.5+ / 1.21+

📸 How It Works (In-Game)

Open a villager’s trade UI normally

Scroll to the bottom

Click the trade:

1 Emerald → ⭐ 🔁 Reroll Trades


Trades reroll instantly

The emerald is NOT consumed

That’s it — no commands, no GUIs, no block breaking.

🧠 Technical Overview
Vanilla-Accurate Trade Rerolling

Instead of defining custom trades, the plugin:

Spawns a temporary villager

Lets Minecraft generate trades naturally

Copies those trades

Applies them to the real villager

Deletes the temporary villager

This ensures:

Enchantments like Mending, Sharpness, etc. work naturally

Prices, levels, and randomness stay vanilla-accurate

Future Minecraft updates automatically work

🧩 Why the “Fake Trade” Approach?

Minecraft does not allow:

Buttons inside the villager UI

Zero-ingredient trades

So the plugin uses a dummy trade:

1 Emerald → ⭐ Reroll Trades


On click:

The trade is cancelled

No items are consumed

Trades reroll safely

This is the only valid, vanilla-safe approach.

📁 Project Structure
VillagerReroll/
├── listener/
│   ├── VillagerOpenListener.java     # Injects reroll trade
│   └── MerchantClickListener.java    # Detects reroll click
│
├── trade/
│   ├── TradeManager.java             # Reroll orchestration
│   ├── VanillaTradeCloner.java       # Vanilla trade cloning
│   └── RerollTrade.java              # Fake reroll trade
│
├── VillagerRerollPlugin.java         # Plugin entry point
├── plugin.yml
└── README.md


This structure follows real production plugin architecture.

⚙️ Installation

Build the plugin:

./gradlew clean build


Copy the JAR from:

build/libs/


Paste it into your server’s:

/plugins


Restart the server

📋 Requirements

Java 17+

PaperMC (recommended)

Minecraft 1.20.5+ / 1.21+

⚠️ Known Behavior (Not a Bug)

After rerolling, the mouse cursor resets to the center

This is vanilla client behavior

Happens whenever a GUI is closed & reopened

Cannot be controlled by plugins

The plugin already handles this in the cleanest possible way.

🔮 Planned Enhancements (Optional)

Cooldown per villager

Configurable emerald / XP cost

Permission-based visibility

Config.yml support

Limit rerolls after trading

🏆 Why This Plugin Is Different

Most villager reroll plugins:

Break job blocks

Fake enchantments

Hardcode trades

Break on updates

VillagerReroll:

Uses Mojang’s own logic

Feels completely vanilla

Is future-proof

Uses clean, maintainable code

📜 License

Free to use, modify, and learn from.
Perfect as a learning project or production plugin base.
