all: build

build:
	./gradlew build
	test -f ../EXE/generated && rm -r ../EXE/generated || true
	test -f ../EXE/reports && rm -r ../EXE/reports || true
	test -f ../EXE/test-results && rm -r ../EXE/test-results || true
	test -f ../EXE/distributions && rm -r ../EXE/distributions || true
	test -f ../EXE/libs && rm -r ../EXE/libs || true
	test -f ../EXE/scripts && rm -r ../EXE/scripts || true
	test -f ../EXE/tmp && rm -r ../EXE/tmp || true
