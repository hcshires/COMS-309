# FoodTime - Meal planning/food inventory Android app - 1_HB_6

Meal planning is hard, and so is keeping track of the ingredients. FoodTime will offer a convenient way to manage what you’re making, and when. It will also offer a convenient “virtual pantry” in order for you to keep track of the ingredients that you have, so you know what you’ll need to buy at the store.

## How it works
One profile per user logged in based on some sort of device ID/fingerprint or provided username? Main sort of calendar screen for the week where you can see an overview of your planned meals. Can tap on entries for more details and ingredient stock. Maybe a tabbed UI for schedule/pantry, or alternatively a sidebar with view selection options, plus a settings section? Pantry section can be a list with quantity.

## Complexity
- Multi-user system will need login ability
- New experience
- Android studio/Springboot is new to us
- Not much database experience so will need to learn there as well
- Could try and use device fingerprint which would be more complex than a simple username but potentially more difficult
= Find recipes based off user's data (avg calorie intake, weight, allergies, etc) if we want to go farther than just manual planning

## Badges
On some READMEs, you may see small images that convey metadata, such as whether or not all the tests are passing for the project. You can use Shields to add some to your README. Many services also have instructions for adding a badge.

## Visuals
Depending on what you are making, it can be a good idea to include screenshots or even a video (you'll frequently see GIFs rather than actual videos). Tools like ttygif can help, but check out Asciinema for a more sophisticated method.

## Installation
This project uses Android Studio with JDK 11 and the following frameworks:
- SpringBoot + ISU VM (for hosting the API and database)
- Android Volley (for frontend API requests)

## Usage
Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably include in the README.

## Support
Tell people where they can go to for help. It can be any combination of an issue tracker, a chat room, an email address, etc.

## Roadmap
If you have ideas for releases in the future, it is a good idea to list them in the README.

## Contributing
State if you are open to contributions and what your requirements are for accepting them.

For people who want to make changes to your project, it's helpful to have some documentation on how to get started. Perhaps there is a script that they should run or some environment variables that they need to set. Make these steps explicit. These instructions could also be useful to your future self.

You can also document commands to lint the code or run tests. These steps help to ensure high code quality and reduce the likelihood that the changes inadvertently break something. Having instructions for running tests is especially helpful if it requires external setup, such as starting a Selenium server for testing in a browser.

## Authors and acknowledgment
Show your appreciation to those who have contributed to the project.

## License
For open source projects, say how it is licensed.

## Project status
If you have run out of energy or time for your project, put a note at the top of the README saying that development has slowed down or stopped completely. Someone may choose to fork your project or volunteer to step in as a maintainer or owner, allowing your project to keep going. You can also make an explicit request for maintainers.
