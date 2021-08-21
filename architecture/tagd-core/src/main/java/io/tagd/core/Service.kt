package io.tagd.core

/**
 * Any object which offers some behaviour, which can be consumed as part of business work flows
 * is a [Service].
 *
 * Services are
 *      - √ State less
 *      - √ Just supports behaviour / methods, but no state (or)
 *      - √ Just supports state, but not behaviour ==>
 *          In a rare phenomenon, service can be a state container, in this scenario it won't offer
 *          any behaviour, it just behaves as a state Lookup. However this is a discouraged or
 *          recommended to limit such practices.
 */
interface Service : Releasable