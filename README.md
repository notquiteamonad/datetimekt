# datetimekt: High-Level Date and Time for Kotlin

[![License][apache-image]][apache]
[![datetimekt on Travis CI][travis-image]][travis]
[![FOSSA Status][fossa-image]][fossa]
[![datetimekt on Jitpack][jitpack-image]][jitpack]

[![Average time to resolve an issue][isitmaintained-resolution-image]][isitmaintained]
[![Percentage of issues still open][isitmaintained-open-image]][isitmaintained]
[![Quality Gate Status][sonarcloud-image]][sonarcloud]
[![CodeFactor][codefactor-image]][codefactor]

[travis-image]: https://travis-ci.org/samueldple/datetimekt.svg?branch=master

[travis]: https://travis-ci.org/samueldple/datetimekt

[apache-image]: https://img.shields.io/badge/License-Apache%202.0-blue.svg

[apache]: https://opensource.org/licenses/Apache-2.0

[fossa]: https://app.fossa.io/projects/git%2Bgithub.com%2Fsamueldple%2Fdatetimekt?ref=badge_shield

[fossa-image]: https://app.fossa.io/api/projects/git%2Bgithub.com%2Fsamueldple%2Fdatetimekt.svg?type=shield

[jitpack]: https://jitpack.io/#samueldple/datetimekt

[jitpack-image]: https://jitpack.io/v/samueldple/datetimekt.svg

[isitmaintained]: http://isitmaintained.com/project/samueldple/datetimekt

[isitmaintained-resolution-image]: http://isitmaintained.com/badge/resolution/samueldple/datetimekt.svg

[isitmaintained-open-image]: http://isitmaintained.com/badge/open/samueldple/datetimekt.svg

[sonarcloud]: https://sonarcloud.io/dashboard?id=samueldple%3Adatetimekt

[sonarcloud-image]: https://sonarcloud.io/api/project_badges/measure?project=samueldple%3Adatetimekt&metric=alert_status

[codefactor]: https://www.codefactor.io/repository/github/samueldple/datetimekt

[codefactor-image]: https://www.codefactor.io/repository/github/samueldple/datetimekt/badge

datetimekt is a high-level Kotlin library for use in situations where
precision beyond seconds is not necessary.

It handles serialisable dates and times from 01 Jan 0000 at 00:00:00 to
Dec 31 9999 at 23:59:59.

**Note:** This README may contain documentation of features which have not been released yet. To ensure
it is accurate for a specific version, use the tag selector and select the version you're using.

[Changelog](https://github.com/samueldple/datetimekt/blob/master/CHANGELOG.md)

[Code of Conduct](https://github.com/samueldple/datetimekt/blob/master/CODE_OF_CONDUCT.md)

## Usage

See [here](https://jitpack.io/#samueldple/datetimekt)

## Overview

This library is a Kotlin port of the rust library [date_time](https://github.com/samueldple/date_time). The two libraries are interoperable in their serialisation from version 2.0.0 of the rust library onwards.

For the differences between them see [the comparison section](#comparison-with-the-rust-library)

### Times

#### Time

Times can be generated using the `Time` class.

Times must either be instantiated using `Time()` which takes either hour, minute, and second parameters or just a total number of seconds. These are then converted into seconds and split apart again to create a time between 00:00:00 and 23:59:59.

`Time` is comparable with itself and `Duration`.

It can also be added to and subtracted from another Time, but the user must be aware that this will loop around midnight such that the time is never 24 hours or greater.

For example:

-   `Time(22, 0, 0) + Time(1, 0, 0)` will produce a `Time` with 23 hours and 0 minutes and seconds.
-   `Time(22, 0, 0) + Time(3, 0, 0)` will produce a `Time` with 1 hour and 0 minutes and seconds.

The current time can be accessed using `Time.now()`.

##### Serialisation

`Time` can be serialised using `toString()` and `toHHMMString()`.

For 8:30:30 AM, the former will produce `"08:30:30"` and the latter will produce `"08:30"`.

A `Time` can be instantiated by calling `Time.fromString()` with a string in the format of `hh:mm:ss`. Invalid strings will return a `null` value.

#### Duration

A second time type, `Duration` exists for cases where a duration should be stored in hours, minutes and seconds. This is similar to the `Time` class but allows hours to be greater than 24.

You can get the duration between two `DateTime`s as follows:

```kotlin
// Gets duration between midnight on 1st Jan 2000 and now.
val dt1 = DateTime.now()
val dt2 = DateTime(Date(2000, 1, 1), Time(0, 0, 0))
Duration.between(dt1, dt2) // Parameter order is irrelevant.
```

### Dates

Dates can be generated using the `Date` and `Month` classes. The `Month` class is similar to `Date` but doesn't include a day of the month.

#### Date

`Date` wraps a year, month, and day of month in a class.

A `Date` can be created using `Date()`, passing a year between 0 and 9999 and a month and date which are valid for that year. Feb 29 can be created if the year is a leap year.

If the month passed in is invalid (not in range 1...12) 12 will be added or subtracted until it is within that range.

If the date passed in is either too small or too high for that month it will be assigned 1 or the last date in that month respectively.

A `Date` can be converted to and from a number of days as an Integer using the `toDays()` and `fromDays()` functions. The number of days referenced is the number of days between 0000-01-01 and the `Date`'s value inclusive.

`Date` is comparable with itself.

Today's date can be accessed via `Date.today()`.

##### Serialisation

`Date` can be serialised using `toString()` and `toReadableString()`.

For 23rd January 2002, the former will produce `"2002-01-23"` and the latter will produce `"23 Jan 2002"`.

A `Date` can be instantiated by calling `Date.fromString()` with a string in the format of `yyyy-mm-dd`. This function will return `null` if the string's format is invalid.

##### Mutation

The correct way to get relative mutations is to convert using toDays and fromDays or modify individual values in a new Date using the constructor.

#### Month

`Month` is identical to `Date` but without a day of the month.

It can be instantiated using `Month()`, passing a year between 0000 and 9999 and a month between 1 and 12.

`Month` is fully comparable with itself.

A `Month` can also be created from a `Date` using either `Date.toMonth()` or `Month.fromDate()`.

##### Mutation

Months can also be mutated by using `addMonths` and `subtractMonths` to move chronologically between months.

These will continue to return the maximum and minimum values of Jan 0000 and Dec 9999 if they are reached.

For example, if a variable `m` contained `Month(2000, 11)` and `m.addMonths(6)` were called, `m` would now contain a months with a year of 2001 and a month of 5 (May).

###### `next_month` and `previous_month`

`Month` provides two methods: `next_month` and `previous_month` which and return the `Month` which chronologically follows or precedes it.

These will continue to return the maximum and minimum values of Jan 0000 and Dec 9999 if they are reached.

These methods consume the existing `Month`.

##### Serialisation

`Month` can be serialised using `toString()` and `toReadableString()`.

For January 2002, the former will produce `"2002-01"` and the latter will produce `"Jan 2002"`.

A `Month` can be instantiated by calling `Month.fromString()` with a string in the format of `yyyy-mm`. An invalid string in this function will result in `null` being returned.

### DateTime

The `DateTime` class wraps a `Date` and a `Time`.

Like the other classes in this library, it is fully comparable with itself.

##### Serialisation

`DateTime` can be serialised using `toString()` and `toReadableString()`.

For 23rd January 2002 at 08:30:30 AM, the former will produce `"2002-01-23@08:30:30"` and the latter will produce `"23 Jan 2002 08:30:30"`.

A `DateTime` can be instantiated by calling `DateTime.fromString()` with a string in the format of `yyyy-mm-dd@hh:mm:ss`. The "@" character can optionally be replaced with a space. An invalid string will result in `null` being returned.

## Comparison with the Rust library

-   The default names of the types in the Rust library are suffixed with "Tuple".
-   The Rust library provides richer mutation functions for the `Date` and `Month` types.
-   The Rust library does not support the "@" in a `DateTime` string being replaced with a space.
-   The Rust library returns error types instead of correcting when invalid values are passed into constructors.

## Limitations

This library was designed for high-level implementations of dates in which precision is not necessary.

For a more feature-rich wrapper of dates, you may wish to use Java LocalDate.

-   This library is only designed for use when dates need only to be precise to the level of seconds.
-   This library is timezone-agnostic; it doesn't deal with any difference between time zones.
-   Only datetimes between `01 Jan 0000 00:00:00` and `31 Dec 9999 23:59:59` are supported.
