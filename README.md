# kata-checkout-order-total

This project is for the PillarTechnology/kata-checkout-order-total challenge.

## How to run 
Built with Gradle and tests executable from the command line with "./gradlew test"

## Side Notes
I built this far enough that it passes scenarios but there are some things it might be missing:
	
	1) Products cannot have decimal weights due to keeping things simple.
	2) I didn't work with seeing what happens if I apply multiple 
		specials for different products to my checkout order.