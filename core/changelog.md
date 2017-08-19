## [2.0.0] - 2017-08-19

### Breaking changes

- `Route` is no longer abstract.
- Moved `RouteBuilder` to `Route`
- Renamed `RouteBuilder` to `Route.Builder`
- Removed `Middlewares` from `Router`. #7
- Removed `AfterMiddleware` and `BeforeMiddleware` declarations from `Input`/`Output` interfaces. They're now in
`Client`.
- Removed deprecated method `Middleware#handle`
- Separated Middlewares in `Route`


### Added

- Added method `adapt(Route<Request, Response>)` to `RouteBuilder`. For a while it has been a great problem
regarding route building and having DRY code, as `build` was not designed to cooperate here. Adapt is meant to assign
all parent (read: super class) `RouteBuilder` fields to that one route you want to build. Each overriding `adapt` method
must call `super.adapt(route)`
- Added `MiddlewareUtility`. This is a static final class that handles all related business to Middlewares. #12
- Added `BeforeRequest`, `AfterResponse`, `Callback`, `Description`, `UsesControllers` and `TestedBy` annotations.
These annotations are used by router and client to determine what is used by application. 
- Added Base controller class. #8
- Added tests for `chatty-core`!

## [1.3.3] - 2017-08-18

### Changed

- Properly packed sources. 

## [1.3.2] - 2017-08-09

### Changed

- Input and output interfaces now have their own corresponding middleware container names.

## [1.3.0-SNAPSHOT] - 2017-08-08

### Changed

- Changed some semantics behind Routes.
- `Middleware` now implements two interfaces: `BeforeMiddleware` and `AfterMiddleware`

### Breaking changes

- `Input` and `Output` are now interfaces.