load("@com_google_j2cl//build_defs:rules.bzl", "j2cl_application", "j2cl_library")

package(
    licenses = ["notice"],  # Apache 2.0
)

j2cl_library(
    name = "app",
    srcs = glob([
        "*.java",
        "*.js",
    ]),
    deps = [
        "@com_google_guava-j2cl",
        "@com_google_j2cl//:jsinterop-annotations-j2cl",
    ],
)

j2cl_application(
    name = "guava",
    entry_points = ["j2cl.samples.app"],
    deps = [":app"],
)
