## [1.3.2] - 2017-08-09

### Changed

- Input and output interfaces now have their own corresponding middleware container names.

## [1.3.0-SNAPSHOT] - 2017-08-08

### Changed

- Changed some semantics behind Routes.
- `Middleware` now implements two interfaces: `BeforeMiddleware` and `AfterMiddleware`

### Breaking changes

- `Input` and `Output` are now interfaces.