Pod::Spec.new do |spec|
    spec.name                     = 'maps_compose'
    spec.version                  = '1.0.0'
    spec.homepage                 = 'https://github.com/grodnoroads/GrodnoRoads'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'Shared functionality for iOS'
    spec.vendored_frameworks      = 'build/cocoapods/framework/maps_compose.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target    = '15.0'
    spec.dependency 'GoogleMaps'
                
    if !Dir.exist?('build/cocoapods/framework/maps_compose.framework') || Dir.empty?('build/cocoapods/framework/maps_compose.framework')
        raise "

        Kotlin framework 'maps_compose' doesn't exist yet, so a proper Xcode project can't be generated.
        'pod install' should be executed after running ':generateDummyFramework' Gradle task:

            ./gradlew :kmp:compose:maps-compose:generateDummyFramework

        Alternatively, proper pod installation is performed during Gradle sync in the IDE (if Podfile location is set)"
    end
                
    spec.xcconfig = {
        'ENABLE_USER_SCRIPT_SANDBOXING' => 'NO',
    }
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':kmp:compose:maps-compose',
        'PRODUCT_MODULE_NAME' => 'maps_compose',
    }
                
    spec.script_phases = [
        {
            :name => 'Build maps_compose',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../../../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
    spec.resources = ['build/compose/cocoapods/compose-resources']
end